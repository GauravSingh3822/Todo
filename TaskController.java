
package com.example.todo.controller;

import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository repo;

    @PostMapping
    public Task create(@RequestBody Task t) {
        return repo.save(t);
    }

    @GetMapping
    public List<Task> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> one(@PathVariable Long id) {
        return repo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Task replace(@PathVariable Long id, @RequestBody Task t) {
        Task existing = repo.findById(id).orElseThrow();
        existing.setName(t.getName());
        existing.setDescription(t.getDescription());
        return repo.save(existing);
    }

    @PatchMapping("/{id}")
    public Task patch(@PathVariable Long id, @RequestBody Task t) {
        Task existing = repo.findById(id).orElseThrow();
        if (t.getName() != null) existing.setName(t.getName());
        if (t.getDescription() != null) existing.setDescription(t.getDescription());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
