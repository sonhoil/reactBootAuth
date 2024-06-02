import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

const Register = ({ onRegisterSuccess }) => {
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [providerId, setProviderId] = useState('');
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    setEmail(params.get('email'));
    setProviderId(params.get('providerId'));
  }, [location]);

  const handleRegister = async () => {
    try {
      // 회원가입 요청
      const response = await axios.post('http://localhost:8080/api/auth/register', { email, nickname, providerId });
      const token = response.data.token;
      console.log(response)
      // JWT 토큰을 로컬 스토리지에 저장
      localStorage.setItem('token', token);

      // 로그인 성공 후 필요한 페이지로 리디렉션 (예: todo 페이지)
      onRegisterSuccess(token);
      navigate('/todos'); // 예시로 todos 페이지로 리디렉션
    } catch (error) {
      console.error('Registration or login error', error);
    }
  };

  return (
    <div>
      <h2>Register</h2>
      <input 
        type="text"
        placeholder="Enter your nickname"
        value={nickname}
        onChange={(e) => setNickname(e.target.value)}
      />
      <button onClick={handleRegister}>Register</button>
    </div>
  );
};

export default Register;
