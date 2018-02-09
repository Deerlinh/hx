package com.jianq.manager.spm.cargo.service;

import com.jianq.manager.spm.cargo.entity.Cargo;

import java.util.List;

public interface CargoService {
    Integer deleteCargo(Integer id);

    Integer insertCargo(Cargo record);

    Cargo selectCargo(Integer id);

    Integer updateCargo(Cargo record);

    List<Cargo> listCargo(Cargo cargo);

}