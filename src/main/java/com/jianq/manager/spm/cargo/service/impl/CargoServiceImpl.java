package com.jianq.manager.spm.cargo.service.impl;

import com.jianq.manager.spm.cargo.dao.CargoMapper;
import com.jianq.manager.spm.cargo.entity.Cargo;
import com.jianq.manager.spm.cargo.service.CargoService;
import com.jianq.manager.util.ListUtil;
import com.jianq.manager.util.StaticPropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Leo on 2017/11/3
 */
@Service
public class CargoServiceImpl implements CargoService {
    private static final Log LOG = LogFactory.getLog(CargoServiceImpl.class);
    private static final String CARGO_URL = StaticPropertiesUtil.getValue("cargo.url.h5");
    @Autowired
    private CargoMapper cargoMapper;

    @Override
    public Integer deleteCargo(Integer id) {
        Integer count = null;
        if (id != null) {
            try {
                count = cargoMapper.deleteByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 deleteCargo 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Integer insertCargo(Cargo cargo) {
        Integer count = null;
        if (cargo != null) {
            try {
                count = cargoMapper.insertSelective(cargo);
            } catch (Exception e) {
                LOG.error("方法 insertCargo 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Cargo selectCargo(Integer id) {
        Cargo cargo = null;
        if (id != null) {
            try {
                cargo = cargoMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 selectCargo 异常,{}", e);
            }
        }
        return completCargoUrl(cargo);
    }

    @Override
    public Integer updateCargo(Cargo cargo) {
        Integer count = null;
        if (cargo != null) {
            try {
                count = cargoMapper.updateByPrimaryKeySelective(cargo);
            } catch (Exception e) {
                LOG.error("方法 updateCargo 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public List<Cargo> listCargo(Cargo cargo) {
        List<Cargo> cargoList = null;
        if (cargo != null) {
            try {
                cargoList = cargoMapper.listCargo(cargo);
            } catch (Exception e) {
                LOG.error("方法 listCargo 异常,{}", e);
            }
        }
        return completCargoListUrl(cargoList);
    }

    private Cargo completCargoUrl(Cargo cargo) {
        if (cargo != null) {
            cargo.setCargoUrl(CARGO_URL + cargo.getId());
        }
        return cargo;
    }

    private List<Cargo> completCargoListUrl(List<Cargo> cargoList) {
        if (ListUtil.isNotEmpty(cargoList)) {
            for (Cargo cargo : cargoList) {
                this.completCargoUrl(cargo);
            }
        }
        return cargoList;
    }
}
