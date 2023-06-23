package com.fz.mall.search;

import com.alibaba.fastjson2.JSON;
import com.fz.mall.common.data.bo.EsSkuBO;
import com.fz.mall.search.constant.EsConstants;
import com.fz.mall.search.pojo.dto.SearchParamDTO;
import com.fz.mall.search.pojo.vo.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @SneakyThrows
    @Test
    public void testEsQuery() {

        GetIndexRequest indexRequest = new GetIndexRequest("hotel");
        GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(indexRequest, RequestOptions.DEFAULT);
        System.out.println(getIndexResponse.getMappings());
    }

    @Test
    public void testSearch() {

        SearchParamDTO searchParamDTO = new SearchParamDTO();

        searchParamDTO.setPage(1);
        searchParamDTO.setKeyword("手机");
        searchParamDTO.setAttrs("8_12GB^9_128GB||256GB");
        searchParamDTO.setBrandIds(Collections.singletonList(3L));
        SearchRequest searchRequest = buildSearchRequest(searchParamDTO);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchResultVO searchResultVO = parseSearchRepose(searchResponse, searchParamDTO);
            System.out.println(searchResultVO);

        } catch (Exception e) {
            System.out.println("Es查询失败");
        }

    }

    private SearchResultVO parseSearchRepose(SearchResponse searchResponse, SearchParamDTO param) {

        SearchResultVO searchResultVO = new SearchResultVO();
        List<SearchSkuVO> searchSkuVOS = new ArrayList<>();

        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits().value;
        searchResultVO.setTotal((int) total);
        searchResultVO.setPages((int) (total % 60 == 0 ? total / 60 : total / 60 + 1));
        searchResultVO.setPage(param.getPage());

        for (SearchHit hit : hits.getHits()) {

            String source = hit.getSourceAsString();
            EsSkuBO esSkuBO = JSON.parseObject(source, EsSkuBO.class);
            List<SearchSkuVO.Attrs> attrVo = esSkuBO.getAttrs().stream().map((attrs -> {
                SearchSkuVO.Attrs attrsVo = new SearchSkuVO.Attrs();
                BeanUtils.copyProperties(attrs, attrsVo);
                return attrsVo;
            })).collect(Collectors.toList());

            SearchSkuVO searchSkuVO = new SearchSkuVO();
            BeanUtils.copyProperties(esSkuBO, searchSkuVO, "attrs");
            searchSkuVO.setAttrs(attrVo);
            searchSkuVOS.add(searchSkuVO);

            HighlightField highlightField = hit.getHighlightFields().get(EsConstants.SKU_TITLE);
            if (highlightField != null && ObjectUtils.isNotEmpty(highlightField.getFragments())) {
                String highlightTitle = highlightField.getFragments()[0].string();
                searchSkuVO.setSkuTitle(highlightTitle);
            }
        }
        searchResultVO.setGoods(searchSkuVOS);

        Aggregations aggregations = searchResponse.getAggregations();

        // 分类聚合
        List<CategoryVO> categoryVOS = new ArrayList<>();
        Terms categoryAgg = aggregations.get(EsConstants.CATEGORY_AGG);
        List<? extends Terms.Bucket> buckets = categoryAgg.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setCategoryId(Long.valueOf(bucket.getKeyAsString()));

            Terms categoryNameAgg = bucket.getAggregations().get(EsConstants.CATEGORY_NAME_AGG);
            List<? extends Terms.Bucket> categoryNameAggBuckets = categoryNameAgg.getBuckets();
            if (ObjectUtils.isNotEmpty(categoryNameAggBuckets)) {
                String categoryName = categoryNameAggBuckets.get(0).getKeyAsString();
                categoryVO.setCategoryName(categoryName);
            }
            categoryVOS.add(categoryVO);
        }
        if (categoryVOS.size() > 1)
            searchResultVO.setCategories(categoryVOS);

        // 品牌聚合
        List<BrandVO> brandVOS = new ArrayList<>();
        Terms brandAgg = aggregations.get(EsConstants.BRAND_AGG);
        List<? extends Terms.Bucket> brandAggBuckets = brandAgg.getBuckets();
        for (Terms.Bucket bucket : brandAggBuckets) {
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandId(Long.valueOf(bucket.getKeyAsString()));

            Terms brandNameAgg = bucket.getAggregations().get(EsConstants.BRAND_NAME_AGG);
            List<? extends Terms.Bucket> brandNameAggBuckets = brandNameAgg.getBuckets();
            if (ObjectUtils.isNotEmpty(brandNameAggBuckets)) {
                String brandName = brandAggBuckets.get(0).getKeyAsString();
                brandVO.setBrandName(brandName);
            }

            Terms brandImgAgg = bucket.getAggregations().get(EsConstants.BRAND_IMG_AGG);
            List<? extends Terms.Bucket> brandImgAggBuckets = brandImgAgg.getBuckets();
            if (ObjectUtils.isNotEmpty(brandImgAggBuckets)) {
                brandVO.setBrandImg(brandImgAggBuckets.get(0).getKeyAsString());
            }
            brandVOS.add(brandVO);
        }
        if (brandVOS.size() > 1)
            searchResultVO.setBrands(brandVOS);

        // 属性聚合
        List<AttrVO> attrVOS = new ArrayList<>();
        ParsedNested attrAgg = aggregations.get(EsConstants.ATTR_AGG);
        Terms attrIdAgg = attrAgg.getAggregations().get(EsConstants.ATTR_ID_AGG);
        List<? extends Terms.Bucket> attrIdAggBuckets = attrIdAgg.getBuckets();
        for (Terms.Bucket attrIdAggBucket : attrIdAggBuckets) {
            AttrVO attrVO = new AttrVO();
            attrVO.setAttrId(Long.valueOf(attrIdAggBucket.getKeyAsString()));
            Terms attrNameAgg = attrIdAggBucket.getAggregations().get(EsConstants.ATTR_NAME_AGG);
            attrVO.setAttrName(attrNameAgg.getBuckets().get(0).getKeyAsString());
            Terms attrValueAgg = attrIdAggBucket.getAggregations().get(EsConstants.ATTR_VALUE_AGG);
            List<String> attrValues = attrValueAgg.getBuckets().stream().map(Terms.Bucket::getKeyAsString).collect(Collectors.toList());
            attrVO.setAttrValues(attrValues);
            if (attrValues.size() > 1)
                attrVOS.add(attrVO);
        }

        searchResultVO.setAttrs(attrVOS);

        return searchResultVO;
    }

    private SearchRequest buildSearchRequest(SearchParamDTO searchParamDTO) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        Optional<String> keyword = Optional.ofNullable(searchParamDTO.getKeyword());
        boolQueryBuilder.must(QueryBuilders.matchQuery(EsConstants.SKU_TITLE, keyword.orElse("")));

        if (searchParamDTO.getCid() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(EsConstants.CATEGORY_ID, searchParamDTO.getCid()));
        }

        if (ObjectUtils.isNotEmpty(searchParamDTO.getBrandIds())) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery(EsConstants.BRAND_ID, searchParamDTO.getBrandIds()));
        }

        boolQueryBuilder.filter(QueryBuilders.termsQuery(EsConstants.HAS_STOCK, searchParamDTO.getHasStock()));


        // 价格区间

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(EsConstants.SKU_PRICE);


        if (searchParamDTO.getLtePrice() != null && searchParamDTO.getLtePrice() >= 0)
            rangeQueryBuilder.lte(searchParamDTO.getLtePrice());


        if (searchParamDTO.getGtePrice() != null && searchParamDTO.getGtePrice() >= 0)
            rangeQueryBuilder.gte(searchParamDTO.getGtePrice());

        boolQueryBuilder.filter(rangeQueryBuilder);

        if (StringUtils.isNotBlank(searchParamDTO.getAttrs())) {
            String[] attrsArr = searchParamDTO.getAttrs().split("\\^");
            if (ObjectUtils.isNotEmpty(attrsArr)) {
                for (String attrWithId : attrsArr) {
                    String[] attrWithIdArr = attrWithId.split("_");
                    if (attrWithIdArr.length == 2 && StringUtils.isNotBlank(attrWithIdArr[0]) && StringUtils.isNotBlank(attrWithIdArr[1])) {

                        List<String> attrValues = new ArrayList<>();
                        if (attrWithIdArr[1].contains("||")) {
                            String[] attrArr = attrWithIdArr[1].split("\\|\\|");
                            attrValues.addAll(Arrays.asList(attrArr));
                        } else attrValues.add(attrWithIdArr[1]);

                        // 对于嵌套类型的数据 需要QueryBuilders.nestedQuery()方法
                        BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                        nestedBoolQuery.must(QueryBuilders.termQuery(EsConstants.ATTRS + EsConstants.ATTR_ID, attrWithIdArr[0]));
                        nestedBoolQuery.must(QueryBuilders.termsQuery(EsConstants.ATTRS + EsConstants.ATTR_VALUE, attrValues));
                        boolQueryBuilder.filter(QueryBuilders.nestedQuery(EsConstants.ATTRS, nestedBoolQuery, ScoreMode.None));
                    }
                }
            }
        }

        if (StringUtils.isNotBlank(searchParamDTO.getSort())) {
            String[] sortArr = searchParamDTO.getSort().split("_");
            SortOrder sortOrder = sortArr[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            searchSourceBuilder.sort(sortArr[0], sortOrder);
        }

        Optional<Integer> page = Optional.ofNullable(searchParamDTO.getPage());
        searchSourceBuilder
                .from((page.orElse(1) - 1) * searchParamDTO.getSize())
                .size(searchParamDTO.getSize());


        if (StringUtils.isNotBlank(searchParamDTO.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field(EsConstants.SKU_TITLE)
                    .preTags("<b style='color:red'>")
                    .postTags("</b>")
                    .requireFieldMatch(false);

            searchSourceBuilder.highlighter(highlightBuilder);
        }


        TermsAggregationBuilder brandAggregation = AggregationBuilders
                .terms(EsConstants.BRAND_AGG)
                .field(EsConstants.BRAND_ID)
                .size(100)
                .subAggregation(AggregationBuilders.terms(EsConstants.BRAND_NAME_AGG).field(EsConstants.BRAND_NAME))
                .subAggregation(AggregationBuilders.terms(EsConstants.BRAND_IMG_AGG).field(EsConstants.BRAND_IMG));


        TermsAggregationBuilder categoryAggregation = AggregationBuilders
                .terms(EsConstants.CATEGORY_AGG)
                .field(EsConstants.CATEGORY_ID)
                .size(100)
                .subAggregation(AggregationBuilders.terms(EsConstants.CATEGORY_NAME_AGG).field(EsConstants.CATEGORY_NAME).size(1));

        TermsAggregationBuilder attrAggregation = AggregationBuilders
                .terms(EsConstants.ATTR_ID_AGG)
                .field(EsConstants.ATTRS + EsConstants.ATTR_ID)
                .subAggregation(AggregationBuilders.terms(EsConstants.ATTR_NAME_AGG).field(EsConstants.ATTRS + EsConstants.ATTR_NAME).size(1))
                .subAggregation(AggregationBuilders.terms(EsConstants.ATTR_VALUE_AGG).field(EsConstants.ATTRS + EsConstants.ATTR_VALUE).size(200))
                .size(100);

        NestedAggregationBuilder attrNestedAgg = AggregationBuilders
                .nested(EsConstants.ATTR_AGG, EsConstants.ATTRS)
                .subAggregation(attrAggregation);

        searchSourceBuilder
                .aggregation(brandAggregation)
                .aggregation(categoryAggregation)
                .aggregation(attrNestedAgg);

        searchSourceBuilder.query(boolQueryBuilder);
        System.out.println(searchSourceBuilder);

        return new SearchRequest(new String[]{EsConstants.INDEX_GOODS}, searchSourceBuilder);
    }

    @Test
    public void testSplit() {

        EsSkuBO skuBO = new EsSkuBO();
        skuBO.setBrandName("hhh");
        EsSkuBO.Attrs attrs = new EsSkuBO.Attrs();
        attrs.setAttrId(10L);
        skuBO.setAttrs(Collections.singletonList(attrs));

        SearchSkuVO searchSkuVO = new SearchSkuVO();

        BeanUtils.copyProperties(skuBO, searchSkuVO);
        System.out.println(searchSkuVO);

    }
}
