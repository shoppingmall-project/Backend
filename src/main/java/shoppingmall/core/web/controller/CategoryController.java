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
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    //카테고리 리스트 조회
    @GetMapping()
    public ResponseDto findAllCategory() {
        return categoryService.findAllCategory();
    }

}
