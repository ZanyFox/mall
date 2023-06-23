package com.fz.mall.cart.controller.mvc;

import com.fz.mall.cart.model.Cart;
import com.fz.mall.cart.model.CartItem;
import com.fz.mall.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartPageController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/cart.html")
    public String cartListPage(Model model) {

        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList";
    }

    @RequestMapping("/addCartItem")
    public String add(RedirectAttributes redirectAttributes, @RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {

        CartItem cartItem = cartService.addCartItem(skuId, num);
        // 重定向传数据
        redirectAttributes.addAttribute("skuId", skuId);
        // 将数据放到session中 可以在页面中取出  只能取一次 和 SpringSession一起使用会报错  LinkedHashMap cannot be cast to org.springframework.web.servlet.FlashMap
        // redirectAttributes.addFlashAttribute();
        return "redirect:http://cart.mallmall.com/success.html";
    }

    @RequestMapping("/success.html")
    public String success(@RequestParam("skuId") Long skuId, Model model) {

        CartItem cartItem = cartService.getCartItemBySkuId(skuId);
        model.addAttribute("cartItem", cartItem);
        return "success";
    }
}
