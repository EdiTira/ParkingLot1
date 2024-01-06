package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.common.CarPhotoDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.CarPhoto;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> copyCarsToDto(List<Car> cars){
        List<CarDto> carsDto = new ArrayList<>();
        cars.forEach(car -> carsDto.add(new CarDto(
                car.getId(),
                car.getLicensePlate(),
                car.getParkingSpot(),
                car.getOwner().getUsername())));
        return carsDto;
    }

    public List<CarDto> findAllCars(){
        try {
            LOG.info("findAllCars");
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex){
            throw new EJBException(ex);
        }
    }

    public void addCar(String licensePlate, String parkingSpot, Long userId){
        try {
            LOG.info("createCar");
            Car car = new Car();
            car.setLicensePlate(licensePlate);
            car.setParkingSpot(parkingSpot);

            User user = entityManager.find(User.class, userId);
            user.getCars().add(car);
            car.setOwner(user);
            entityManager.persist(car);
        } catch (Exception ex){
            throw new EJBException(ex);
        }
    }

    public CarDto findById(Long id){
        try {
            LOG.info("findById");
            Car car = entityManager.find(Car.class, id);
            return new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername());
        } catch (Exception ex){
            throw new EJBException(ex);
        }
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId){
        try {
            LOG.info("updateCar");
            Car car = entityManager.find(Car.class, carId);
            car.setLicensePlate(licensePlate);
            car.setParkingSpot(parkingSpot);

            User olduser = car.getOwner();
            olduser.getCars().remove(car);

            User user = entityManager.find(User.class, userId);
            user.getCars().add(car);
            car.setOwner(user);
        } catch (Exception ex){
            throw new EJBException(ex);
        }
    }

    public void deleteCars(List<Long> carIds){
        try {
            LOG.info("deleteCars");
            for (Long carId : carIds) {
                Car car = entityManager.find(Car.class, carId);
                User user = car.getOwner();
                user.getCars().remove(car);
                entityManager.remove(car);
            }
        } catch (Exception ex){
            throw new EJBException(ex);
        }
    }

    public void addPhotoToCar(Long carId, String filename, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToCar");
        CarPhoto photo = new CarPhoto();
        photo.setFilename(filename);
        photo.setFileType(fileType);
        photo.setFileContent(fileContent);

        Car car = entityManager.find(Car.class, carId);
        if (car.getPhoto() != null) {
            entityManager.remove(car.getPhoto());
        }
        car.setPhoto(photo);

        photo.setCar(car);
        entityManager.persist(photo);
    }

    public CarPhotoDto findPhotoByCarId(Long carId) {
        List<CarPhoto> photos = entityManager
                .createQuery("SELECT p FROM CarPhoto p where p.car.id = :id", CarPhoto.class)
                .setParameter("id", carId)
                .getResultList();

        if (photos.isEmpty()) {
            return null;
        }

        CarPhoto photo = photos.get(0);

        return new CarPhotoDto(photo.getId(), photo.getFilename(), photo.getFileType(),
                photo.getFileContent());
    }
}
