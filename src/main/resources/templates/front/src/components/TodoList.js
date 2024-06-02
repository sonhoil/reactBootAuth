import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TodoItem from './TodoItem';
import TodoForm from './TodoForm';

function TodoList() {
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    axios.get('/api/todos/1')  // Assuming userId 1 for demonstration
      .then(response => {
        setTodos(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the todos!', error);
      });
  }, []);

  const addTodo = (todo) => {
    axios.post('/api/todos', todo)
      .then(response => {
        setTodos([...todos, response.data]);
      })
      .catch(error => {
        console.error('There was an error adding the todo!', error);
      });
  };

  const updateTodo = (id, updatedTodo) => {
    axios.put(`/api/todos/${id}`, updatedTodo)
      .then(response => {
        setTodos(todos.map(todo => (todo.id === id ? response.data : todo)));
      })
      .catch(error => {
        console.error('There was an error updating the todo!', error);
      });
  };

  const deleteTodo = (id) => {
    axios.delete(`/api/todos/${id}`)
      .then(() => {
        setTodos(todos.filter(todo => todo.id !== id));
      })
      .catch(error => {
        console.error('There was an error deleting the todo!', error);
      });
  };

  return (
    <div>
      <TodoForm addTodo={addTodo} />
      <ul>
        {todos.map(todo => (
          <TodoItem key={todo.id} todo={todo} updateTodo={updateTodo} deleteTodo={deleteTodo} />
        ))}
      </ul>
    </div>
  );
}

export default TodoList;
