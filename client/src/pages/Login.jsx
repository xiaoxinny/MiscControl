import { Button, Container, FormControl, TextField } from "@mui/material";
import { useFormik } from "formik";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as Yup from "yup";
import http from "./http.js";

function Login() {
  const navigate = useNavigate();
  const [error, setError] = useState('');

  const initialValues = {
    email: "",
    password: "",
  };

  const validationSchema = Yup.object().shape({
    email: Yup.string().email("Invalid email").required("Email is required"),
    password: Yup.string().required("Password is required"),
  });

  const handleSubmit = async (values) => {
    try {
      const response = await http.post('/user/login', values);
      localStorage.setItem("token", response.data.token);
      alert(response.data.message);
      navigate("/home");
    } catch (error) {
      if (error.response && error.response.status === 400) {
        setError(error.response.data.message);
      } else {
        setError('An error occurred. Please try again.');
      }
    }
  };

  const formik = useFormik({
    initialValues: initialValues,
    validationSchema: validationSchema,
    onSubmit: handleSubmit,
  });

  return (
      <Container
          sx={{ backgroundColor: "white", borderRadius: 2, boxShadow: 2, pb: 4 }}
      >
        <h1>Login</h1>
        <form onSubmit={formik.handleSubmit}>
          <FormControl fullWidth>
            <TextField
                required
                label="Email"
                id="email"
                name="email"
                type="email"
                value={formik.values.email}
                onChange={formik.handleChange}
                error={formik.touched.email && Boolean(formik.errors.email)}
                helperText={formik.touched.email && formik.errors.email}
                onBlur={formik.handleBlur}
                variant="outlined"
                fullWidth
                sx={{ mt: 2 }}
            />
          </FormControl>
          <FormControl fullWidth sx={{ mt: 2 }}>
            <TextField
                required
                label="Password"
                id="password"
                name="password"
                type="password"
                value={formik.values.password}
                onChange={formik.handleChange}
                error={formik.touched.password && Boolean(formik.errors.password)}
                helperText={formik.touched.password && formik.errors.password}
                onBlur={formik.handleBlur}
                variant="outlined"
                fullWidth
            />
          </FormControl>
          {error && <p style={{ color: 'red' }}>{error}</p>}
          <Button
              variant="contained"
              color="primary"
              fullWidth
              size="large"
              type="submit"
              sx={{ mt: 4 }}
          >
            Login
          </Button>
        </form>
      </Container>
  );
}

export default Login;