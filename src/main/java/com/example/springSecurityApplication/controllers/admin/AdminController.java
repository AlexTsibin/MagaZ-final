package com.example.springSecurityApplication.controllers.admin;

import com.example.springSecurityApplication.models.Category;
import com.example.springSecurityApplication.models.Image;
import com.example.springSecurityApplication.models.Product;
import com.example.springSecurityApplication.models.Order;
import com.example.springSecurityApplication.enumm.Status;
import com.example.springSecurityApplication.repositories.CategoryRepository;
import com.example.springSecurityApplication.repositories.OrderRepository;
import com.example.springSecurityApplication.services.CategoryService;
import com.example.springSecurityApplication.services.ProductService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    private Status statusObj;

    private final ProductService productService;
    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public AdminController(ProductService productService, CategoryRepository categoryRepository, CategoryService categoryService,
                           OrderRepository orderRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.orderRepository = orderRepository;
    }

        @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("")
    public String admin(Model model){

        model.addAttribute("products", productService.getAllProduct());
        return "admin/admin";
    }

    // http:8080/localhost/admin/product/add
    // ?????????? ???? ?????????????????????? ???????????????? ?? ???????????????????????? ???????????????????? ??????????????
    @GetMapping("/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    // ?????????? ???? ???????????????????? ???????????????? ?? ???? ?????????? ????????????->??????????????????????
    @PostMapping("/product/add")
    public String addProduct(
            @ModelAttribute("product") @Valid Product product,
            BindingResult bindingResult,
            @RequestParam("file_one")MultipartFile file_one,
            @RequestParam("file_two")MultipartFile file_two,
            @RequestParam("file_three")MultipartFile file_three,
            @RequestParam("file_four")MultipartFile file_four,
            @RequestParam("file_five") MultipartFile file_five) throws IOException {
        if(bindingResult.hasErrors())
        {
            return "product/addProduct";
        }

        if(file_one != null)
        {
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_two != null)
        {
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_three != null)
        {
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_four != null)
        {
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_five != null)
        {
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        productService.saveProduct(product);
        return "redirect:/admin";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    // ?????????? ???? ?????????????????????? ???????????????? ?? ???????????????????????? ???????????????????????????? ??????????????
    @GetMapping("/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    // ?????????? ???? ???????????????????????????? ????????????
    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
        {
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

    @GetMapping("/category")
    public String listCategory(Model model){
        model.addAttribute("categoryNew", new Category());
        model.addAttribute("category", categoryRepository.findAll());
        return "Category/listCategory";
    }

    @PostMapping("/category/add")
    public String addCategory(
            @ModelAttribute("category") @Valid Category category,
            BindingResult bindingResult) throws IOException{
        if(bindingResult.hasErrors())
        {
            return "Category/listCategory";
        }

        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }
    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }
    @PostMapping("/category/edit/{id}")
    public String editCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
        {
            return "Category/listCategory";
        }
        categoryService.updateCategory(id, category);
        return "redirect:/admin/category";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<String> listOrders = orderRepository.findAllGroupByNumber();
        JSONArray jPordArr = new JSONArray();

        String Status = null;
        for (String order : listOrders) {
            LocalDateTime data = null;
            Status = null;
            Integer sumCount = 0;
            float sumPrice = 0;
            JSONObject jPordObj = new JSONObject();
            JSONArray jProd = new JSONArray();

            List<Order> lOrders = orderRepository.findOrderByNumber(order);
            for (Order or : lOrders) {
                data = or.getDateTime();
                Status = String.valueOf(or.getStatus());

                sumCount = sumCount + or.getCount();
                sumPrice = sumPrice + or.getPrice();

                JSONObject ja = new JSONObject();
                ja.put("data", or.getDateTime());
                ja.put("id", or.getProduct().getId());
                ja.put("name", or.getProduct().getTitle());
                ja.put("count", or.getCount());
                ja.put("price", or.getPrice());

                jProd.put(ja);
                ja = null;
            }

            jPordObj.put("num", order);
            jPordObj.put("date", data);
            jPordObj.put("Status", Status);
            jPordObj.put("sumCount", sumCount);
            jPordObj.put("sumPrice", sumPrice);
            jPordObj.put("products", jProd);
            data = null;
            Status = null;
            jProd = null;

            jPordArr.put(jPordObj);
            jPordObj = null;
        }

        JSONArray jStatusdArr = new JSONArray();
        for (Status st : statusObj.values()){
            jStatusdArr.put(st);
//            System.out.println(st);
        }

        model.addAttribute("ordersObj", jPordArr.toList());
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("statusObj", jStatusdArr);
        return "admin/orders";
    }

    @PostMapping("/orders/editstatus/{id}")
    public String editOrderStatus(@ModelAttribute("statusF") int statusF, BindingResult bindingResult, @PathVariable("id") String id) {

//        System.out.println(statusF);
//        System.out.println(id);

        orderRepository.editOrderStatus(statusF, id);
        return "redirect:/admin/orders";
    }
    @PostMapping("/orders")
    public String SearchByNun(@ModelAttribute("q") String queryString,Model model){

        List<String> listOrders = orderRepository.findByQueryNum(queryString);
        JSONArray jPordArr = new JSONArray();

        String Status = null;
        for (String order : listOrders) {
            LocalDateTime data = null;
            Status = null;
            Integer sumCount = 0;
            float sumPrice = 0;
            JSONObject jPordObj = new JSONObject();
            JSONArray jProd = new JSONArray();

            List<Order> lOrders = orderRepository.findOrderByNumber(order);
            for (Order or : lOrders) {
                data = or.getDateTime();
                Status = String.valueOf(or.getStatus());

                sumCount = sumCount + or.getCount();
                sumPrice = sumPrice + or.getPrice();

                JSONObject ja = new JSONObject();
                ja.put("data", or.getDateTime());
                ja.put("id", or.getProduct().getId());
                ja.put("name", or.getProduct().getTitle());
                ja.put("count", or.getCount());
                ja.put("price", or.getPrice());

                jProd.put(ja);
                ja = null;
            }

            jPordObj.put("num", order);
            jPordObj.put("date", data);
            jPordObj.put("Status", Status);
            jPordObj.put("sumCount", sumCount);
            jPordObj.put("sumPrice", sumPrice);
            jPordObj.put("products", jProd);
            data = null;
            Status = null;
            jProd = null;

            jPordArr.put(jPordObj);
            jPordObj = null;
        }

        JSONArray jStatusdArr = new JSONArray();
        for (Status st : statusObj.values()){
            jStatusdArr.put(st);
//            System.out.println(st);
        }

        model.addAttribute("ordersObj", jPordArr.toList());
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("statusObj", jStatusdArr);
        return "admin/orders";
    }


}

