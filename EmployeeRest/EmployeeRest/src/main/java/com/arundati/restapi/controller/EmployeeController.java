
package com.arundati.restapi.controller;

import com.arundati.restapi.entity.Employee;
import com.arundati.restapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController
{

    @Autowired
    private EmployeeRepository repository;

    @GetMapping
    public List<Employee> getAllEmployees()
    {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id)
    {
        Optional<Employee> emp = repository.findById(id);
        return emp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee emp)
    {
        return repository.save(emp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updated)
    {
        return repository.findById(id).map(emp ->
        {
            emp.setName(updated.getName());
            emp.setDepartment(updated.getDepartment());
            emp.setSalary(updated.getSalary());
            return ResponseEntity.ok(repository.save(emp));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id)
    {
        return repository.findById(id).map(emp ->
        {
            repository.delete(emp);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
