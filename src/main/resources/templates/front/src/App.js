import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import TodoList from './components/TodoList';

const App = () => {
  const handleRegisterSuccess = (token) => {
    localStorage.setItem('token', token);
    window.location.href = '/todos';
  };

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register onRegisterSuccess={handleRegisterSuccess} />} />
        <Route path="/todos" element={<TodoList />} />
      </Routes>
    </Router>
  );
};

export default App;
