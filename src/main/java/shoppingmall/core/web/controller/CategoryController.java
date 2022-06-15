package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.core.service.category.CategoryService;
import shoppingmall.core.web.dto.ResponseDto;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    //카테고리 리스트 조회
    @GetMapping("/category")
    public ResponseDto findAllCategory() {
        return categoryService.findAllCategory();
    }

    @GetMapping("/brand")
    public ResponseDto findAllBrand() {
        return categoryService.findAllBrand();
    }

    @GetMapping("/country")
    public ResponseDto findAllCountry() {
        return categoryService.findAllCountry();
    }

}
