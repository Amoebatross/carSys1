package com.jkxy.car.api.service.Impl;

import com.jkxy.car.api.dao.CarDao;
import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("carService")
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Override
    public List<Car> findAll() {
        return carDao.findAll();
    }

    @Override
    public Car findById(int id) {
        return carDao.findById(id);
    }

    @Override
    public List<Car> findByCarName(String carName) {
        return carDao.findByCarName(carName);
    }

    @Override
    public void deleteById(int id) {
        carDao.deleteById(id);
    }

    @Override
    public void updateById(Car car) {
        carDao.updateById(car);
    }

    @Override
    public void insertCar(Car car) {
        carDao.insertCar(car);
    }

    @Override
    public void buyCar(int id,int num) throws Exception {
        Car car=carDao.findById(id);
        if(car.getCounts()<num){
            throw new Exception("库存不足");
        }
        else{
            try {
                car.setCounts(car.getCounts() - num);
                carDao.buyCar(car);
            }
            catch (Exception e){
                throw new Exception("购买失败");
            }
        }
    }

    @Override
    public List<Car> findByBrand(String brand, int start, int end) {
        return carDao.findByBrand(brand, start-1, end-1);
    }
}
