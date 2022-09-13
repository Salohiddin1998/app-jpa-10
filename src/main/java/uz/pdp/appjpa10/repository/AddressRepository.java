package uz.pdp.appjpa10.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjpa10.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
