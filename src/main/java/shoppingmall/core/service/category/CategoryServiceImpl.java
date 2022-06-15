package shoppingmall.core.service.category;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.web.dto.ResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final GoodsRepository goodsRepository;
    @Override
    public ResponseDto findAllCategory() {
        List<String> categoryList = goodsRepository.findGoodsCategory();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {

            JSONObject data = new JSONObject();
            data.put("category", categoryList.get(i));
            jsonArray.add(i, data);
        }
        return new ResponseDto("SUCCESS", jsonArray);
    }

    @Override
    public ResponseDto findAllBrand() {
        List<String> brandList = goodsRepository.findGoodsBrand();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < brandList.size(); i++) {

            JSONObject data = new JSONObject();
            data.put("brand", brandList.get(i));
            jsonArray.add(i, data);
        }
        return new ResponseDto("SUCCESS", jsonArray);
    }

    @Override
    public ResponseDto findAllCountry() {
        List<String> countryList = goodsRepository.findGoodsCountry();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < countryList.size(); i++) {

            JSONObject data = new JSONObject();
            data.put("country", countryList.get(i));
            jsonArray.add(i, data);
        }
        return new ResponseDto("SUCCESS", jsonArray);
    }

}
