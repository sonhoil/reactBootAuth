import React, { useState } from 'react';

function TodoForm({ addTodo }) {
  const [todo, setTodo] = useState({ title: '', description: '' });

  const handleChange = (event) => {
    setTodo({
      ...todo,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    addTodo(todo);
    setTodo({ title: '', description: '' });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" name="title" value={todo.title} onChange={handleChange} placeholder="Title" />
      <input type="text" name="description" value={todo.description} onChange={handleChange} placeholder="Description" />
      <button type="submit">Add Todo</button>
    </form>
  );
}

export default TodoForm;
