package myspring.com.myspring.Controller;

import lombok.RequiredArgsConstructor;
import myspring.com.myspring.Model.Role;
import myspring.com.myspring.Service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor

public class Rolecontroller {
    private final RoleService roleService;

    // Get all
    @GetMapping
    ResponseEntity<Map> getList(){
        Map res = new HashMap();
        res.put("total",2);
        res.put("list",roleService.getList());
        return ResponseEntity.ok(res);
    }

    // Get one
    @GetMapping("/{id}")
    ResponseEntity<Optional<Role>> getOne(@PathVariable Long id){
        return ResponseEntity.ok(roleService.getOneById(id));
    }

    // Create
    @PostMapping
    ResponseEntity<Role> create(@RequestBody Role role){
        return ResponseEntity.status(201).body(roleService.create(role));
    }

    // Udpate
    @PutMapping("/{id}")
    ResponseEntity<Role> update(@PathVariable Long id, @RequestBody Role role){
        return ResponseEntity.ok(roleService.update(id,role));
    }

    // Delete
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id){
        roleService.delete(id);
        return ResponseEntity.ok("Delete successful");
    }
}
