package com.jkxy.car.api.controller;

import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.service.CarService;
import com.jkxy.car.api.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("car")
public class CarController {
    @Autowired
    private CarService carService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findAll")
    public JSONResult findAll() {
        List<Car> cars = carService.findAll();
        return JSONResult.ok(cars);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public JSONResult findById(@PathVariable int id) {
        Car car = carService.findById(id);
        return JSONResult.ok(car);
    }

    /**
     * 通过车名查询
     *
     * @param carName
     * @return
     */
    @GetMapping("findByCarName/{carName}")
    public JSONResult findByCarName(@PathVariable String carName) {
        List<Car> cars = carService.findByCarName(carName);
        return JSONResult.ok(cars);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById/{id}")
    public JSONResult deleteById(@PathVariable int id) {
        carService.deleteById(id);
        return JSONResult.ok();
    }

    /**
     * 通过id更新全部信息
     *
     * @return
     */
    @PostMapping("updateById")
    public JSONResult updateById(Car car) {
        carService.updateById(car);
        return JSONResult.ok();
    }

    /**
     * 通过id增加
     *
     * @param car
     * @return
     */
    @PostMapping("insertCar")
    public JSONResult insertCar(Car car) {
        carService.insertCar(car);
        return JSONResult.ok();
    }

    //购买车辆 购买后车辆减少
    @GetMapping("buyCar/{carType}/{quantity}")
    public JSONResult buyCar(@PathVariable String carType, @PathVariable Integer quantity) {
        List<Car> typeCars = carService.findByCarType(carType);
        int realQuantity = 0;
        for (int i = 0; i < typeCars.size(); i++){
            realQuantity += typeCars.get(i).getCounts();
            if(realQuantity > quantity){
                typeCars.get(i).setCounts(realQuantity - quantity);
                carService.updateById(typeCars.get(i));
                return JSONResult.ok();
            }else {
                if(i < typeCars.size() - 1){
                    typeCars.get(i).setCounts(0);
                    carService.updateById(typeCars.get(i));
                }else {
                    return JSONResult.errorException( "库存不足");
                }
            }
        }
        return JSONResult.ok();
    }

    //对品牌车辆模糊查询
    @GetMapping("findByBrand/{carName}/{pageSize}/{pageNumber}")
    public JSONResult findByBrand(@PathVariable String carName, @PathVariable Integer pageSize, @PathVariable Integer pageNumber) {
        List<Car> cars = carService.findByBrand(carName, (pageNumber - 1) * pageSize, pageSize);
        return JSONResult.ok(cars);
    }
}
