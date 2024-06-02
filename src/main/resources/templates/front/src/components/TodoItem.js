import React from 'react';

function TodoItem({ todo, updateTodo, deleteTodo }) {
  const handleChange = (event) => {
    updateTodo(todo.id, { ...todo, [event.target.name]: event.target.value });
  };

  return (
    <li>
      <input type="text" name="title" value={todo.title} onChange={handleChange} />
      <input type="text" name="description" value={todo.description} onChange={handleChange} />
      <button onClick={() => deleteTodo(todo.id)}>Delete</button>
    </li>
  );
}

export default TodoItem;
