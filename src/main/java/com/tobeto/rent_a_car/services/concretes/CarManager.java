package com.tobeto.rent_a_car.services.concretes;

import com.tobeto.rent_a_car.entities.Car;
import com.tobeto.rent_a_car.repositories.CarRepository;
import com.tobeto.rent_a_car.services.abstracts.CarService;
import com.tobeto.rent_a_car.services.dtos.car.requests.AddCarRequest;
import com.tobeto.rent_a_car.services.dtos.car.requests.UpdateCarRequest;
import com.tobeto.rent_a_car.services.dtos.car.responses.GetAllCarsResponse;
import com.tobeto.rent_a_car.services.dtos.car.responses.GetCarResponse;
import com.tobeto.rent_a_car.services.dtos.car.responses.GetListCarResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarManager implements CarService{

    private final CarRepository carRepository;

    public CarManager(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
public List<GetAllCarsResponse> getAll() {
        List<Car> carList = carRepository.findAll();
        List<GetAllCarsResponse> getAllCarsResponseList = new ArrayList<>();
        for (Car car : carList) {
            GetAllCarsResponse getAllCarsResponse = new GetAllCarsResponse();
            getAllCarsResponse.setId(car.getId());
            getAllCarsResponse.setBrand(car.getBrand());
            getAllCarsResponse.setModelName(car.getModelName());
            getAllCarsResponse.setModelYear(car.getModelYear());
            getAllCarsResponse.setColor(car.getColor());
            getAllCarsResponse.setType(car.getType());
            getAllCarsResponse.setPrice(car.getPrice());
            getAllCarsResponse.setStatus(car.getStatus());
            getAllCarsResponseList.add(getAllCarsResponse);
        }
        return getAllCarsResponseList;
}

    @Override
    public GetCarResponse getById(int id) {
        Car carToId = carRepository.findById(id).orElseThrow();
        GetCarResponse getCarResponse = new GetCarResponse();
        getCarResponse.setBrand(carToId.getBrand());
        getCarResponse.setModelName(carToId.getModelName());
        getCarResponse.setModelYear(carToId.getModelYear());
        getCarResponse.setColor(carToId.getColor());
        getCarResponse.setType(carToId.getType());
        getCarResponse.setPrice(carToId.getPrice());
        getCarResponse.setStatus(carToId.getStatus());
        return getCarResponse;
    }

    @Override
    public void delete(int id) {
        Car carToDelete=carRepository.findById(id).orElseThrow();
        carRepository.delete(carToDelete);
    }

    @Override
    public void add(AddCarRequest addCarRequest) {
        Car car = new Car();
        car.setBrand(addCarRequest.getBrandId());
        car.setModelName(addCarRequest.getModelName());
        car.setModelYear(addCarRequest.getModelYear());
        car.setColor(addCarRequest.getColorId());
        car.setType(addCarRequest.getTypeId());
        car.setPrice(addCarRequest.getPriceId());
        car.setStatus(addCarRequest.getStatus());
        carRepository.save(car);
    }

    @Override
    public void update(UpdateCarRequest updateCarRequest) {

        Car carToUpdate = carRepository.findById(updateCarRequest.getId()).orElseThrow();
        carToUpdate.setBrand(updateCarRequest.getBrandId());
        carToUpdate.setModelName(updateCarRequest.getModelName());
        carToUpdate.setModelYear(updateCarRequest.getModelYear());
        carToUpdate.setColor(updateCarRequest.getColorId());
        carToUpdate.setType(updateCarRequest.getTypeId());
        carToUpdate.setPrice(updateCarRequest.getPriceId());
        carToUpdate.setStatus(updateCarRequest.getStatus());
        carRepository.save(carToUpdate);
    }

    @Override
    public List<Car> getByModelName(String modelName) {
        return carRepository.findByModelName(modelName);
    }

    @Override
    public List<GetListCarResponse> getByStatus() {
       return carRepository.findByStatus().stream().map((car)->{
           return new GetListCarResponse(car.getId(),car.getModelYear(), car.getModelName(), car.getStatus(), car.getPriceId(),car.getBrandId(), car.getColorId(), car.getTypeId());
       }).toList();

        //return carRepository.findByStatus();
    }

    @Override
    public List<GetListCarResponse> getByOrderBymodelYear() {
        return  carRepository.findByOrderBymodelYear();
    }


    @Override
    public List<GetListCarResponse> getByBrandAndModelName() {

        return carRepository.getByBrandAndModelName();
    }
}
