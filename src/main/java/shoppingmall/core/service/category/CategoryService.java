package shoppingmall.core.service.category;

import shoppingmall.core.web.dto.ResponseDto;

public interface CategoryService {

    ResponseDto findAllCategory();

    ResponseDto findAllBrand();

    ResponseDto findAllCountry();
}
