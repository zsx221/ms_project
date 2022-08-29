package xhdProject.ms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xhdProject.ms.Dao.GoodsDao;
import xhdProject.ms.domin.Miaosha_goods;
import xhdProject.ms.vo.GoodsVo;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        // TODO Auto-generated method stub
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goodsVo) {
        // TODO Auto-generated method stub
        Miaosha_goods g = new Miaosha_goods();
        g.setGoodsId(goodsVo.getId());
        //g.setStock_count(goodsVo.getGoodsStock()-1);
        int i = goodsDao.reduceStock(g);
        if (i != 0) {
            return true;
        } else {
            return false;
        }
    }
}
