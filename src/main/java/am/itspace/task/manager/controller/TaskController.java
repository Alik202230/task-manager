package am.itspace.task.manager.controller;

import am.itspace.task.manager.dto.CreateTaskRequest;
import am.itspace.task.manager.dto.CreateTaskResponse;
import am.itspace.task.manager.dto.PageResponse;
import am.itspace.task.manager.dto.TaskResponse;
import am.itspace.task.manager.dto.UpdateTaskRequest;
import am.itspace.task.manager.dto.UpdatedTaskResponse;
import am.itspace.task.manager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

  private final TaskService taskService;

  @PostMapping("/create")
  public ResponseEntity<CreateTaskResponse> create(@RequestBody @Valid CreateTaskRequest request) {
    CreateTaskResponse response = this.taskService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<PageResponse<TaskResponse>> getAllTasks(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    PageResponse<TaskResponse> tasks = taskService.getAllTasks(page, size);
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<TaskResponse>> getTaskById(@PathVariable Long id) {
    Optional<TaskResponse> taskResponse = taskService.getTaskById(id);
    return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UpdatedTaskResponse> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
    UpdatedTaskResponse updatedTask = taskService.updateTask(request, id);
    return ResponseEntity.ok(updatedTask);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    this.taskService.deleteTaskById(id);
    return ResponseEntity.status(HttpStatus.OK).body("Task with " + id + " deleted successfully");
  }

}
