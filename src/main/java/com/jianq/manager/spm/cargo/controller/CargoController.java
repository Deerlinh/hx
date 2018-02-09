package com.jianq.manager.spm.cargo.controller;

import com.jianq.manager.spm.cargo.entity.Cargo;
import com.jianq.manager.spm.cargo.service.CargoService;
import com.jianq.manager.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leo on 2017/11/3
 */
@RestController
@RequestMapping("/**/cargo")
public class CargoController {
    @Autowired
    private CargoService cargoService;

    @RequestMapping("/list")
    public Object listCargo(Cargo cargo) {
        List<Cargo> cargoList = cargoService.listCargo(cargo);
        return ReturnUtil.success(cargoList);
    }

    @RequestMapping("/info")
    public Object selectCargo(Integer cargoId) {
        Cargo cargo = cargoService.selectCargo(cargoId);
        return ReturnUtil.success(cargo);
    }
}
