import {Button, Container, FormControl, TextField, FormHelperText} from "@mui/material";
import {useFormik} from "formik";
import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import * as Yup from "yup";
import http from "./http.js";
import zxcvbn from "zxcvbn";

function Registration() {
    const navigate = useNavigate();
    const [selectedFileName, setSelectedFileName] = useState("");
    const [passwordStrength, setPasswordStrength] = useState(null);
    const [error, setError] = useState('');

    const initialValues = {
        firstName: "",
        lastName: "",
        creditCard: "",
        phoneNumber: "",
        billingAddress: "",
        shippingAddress: "",
        email: "",
        password: "",
        confirmPassword: "",
        imageBase64: null,
    };

    const handleFileChange = (event) => {
        const file = event.currentTarget.files[0];
        if (file) {
            setSelectedFileName(file.name);
            const reader = new FileReader();
            reader.onloadend = () => {
                formik.setFieldValue("imageBase64", reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const validationSchema = Yup.object().shape({
        firstName: Yup.string().required("First Name is required"),
        lastName: Yup.string().required("Last Name is required"),
        creditCard: Yup.string()
            .matches(/^\d{16}$/, "Credit Card Number must be 16 digits")
            .required("Credit Card Number is required"),
        phoneNumber: Yup.string()
            .matches(/^\d{8}$/, "Mobile Number must be 8 digits")
            .required("Mobile Number is required"),
        billingAddress: Yup.string().required("Billing Address is required"),
        shippingAddress: Yup.string().required("Shipping Address is required"),
        email: Yup.string().email("Invalid email").required("Email is required"),
        password: Yup.string()
            .min(12, "Password must be at least 12 characters")
            .matches(/[a-z]/, "Password must contain at least one lowercase letter")
            .matches(/[A-Z]/, "Password must contain at least one uppercase letter")
            .matches(/[0-9]/, "Password must contain at least one number")
            .matches(/[^a-zA-Z0-9]/, "Password must contain at least one special character")
            .required("Password is required"),
        confirmPassword: Yup.string()
            .oneOf([Yup.ref("password"), null], "Passwords must match")
            .required("Confirm Password is required")
    });

    const handleSubmit = async (values) => {
        if (!values.imageBase64) {
            setError("Profile picture is required.");
            return;
        }

        // Convert all values to strings
        const stringValues = Object.fromEntries(
            Object.entries(values).map(([key, value]) => [key, value !== null ? String(value) : ""])
        );

        console.log("Form values:", stringValues); // Log form values
        try {
            const response = await http.post('/user/registration', stringValues);
            alert(response.data.message);
            navigate("/login");
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

    const handleReset = () => {
        formik.resetForm();
        setSelectedFileName("");
        setPasswordStrength(null);
    };

    const handlePasswordChange = (event) => {
        const password = event.target.value;
        formik.handleChange(event);
        const result = zxcvbn(password);
        setPasswordStrength(result.score);
    };

    return (
        <Container
            sx={{backgroundColor: "white", borderRadius: 2, boxShadow: 2, pb: 4}}
        >
            <h1>Registration</h1>
            <form onSubmit={formik.handleSubmit}>
                <FormControl fullWidth>
                    <TextField
                        required
                        label="First Name"
                        id="firstName"
                        name="firstName"
                        value={formik.values.firstName}
                        onChange={formik.handleChange}
                        error={formik.touched.firstName && Boolean(formik.errors.firstName)}
                        helperText={formik.touched.firstName && formik.errors.firstName}
                        onBlur={formik.handleBlur}
                        variant="outlined"
                        fullWidth
                        sx={{mt: 2}}
                    />
                </FormControl>
                <FormControl fullWidth>
                    <TextField
                        required
                        label="Last Name"
                        id="lastName"
                        name="lastName"
                        value={formik.values.lastName}
                        onChange={formik.handleChange}
                        error={formik.touched.lastName && Boolean(formik.errors.lastName)}
                        helperText={formik.touched.lastName && formik.errors.lastName}
                        onBlur={formik.handleBlur}
                        variant="outlined"
                        fullWidth
                        sx={{mt: 2}}
                    />
                </FormControl>
                <FormControl fullWidth>
                    <TextField
                        required
                        label="Credit Card No."
                        id="creditCard"
                        name="creditCard"
                        type="number"
                        value={formik.values.creditCard}
                        onChange={formik.handleChange}
                        error={
                            formik.touched.creditCard &&
                            Boolean(formik.errors.creditCard)
                        }
                        helperText={
                            formik.touched.creditCard && formik.errors.creditCard
                        }
                        onBlur={formik.handleBlur}
                        variant="outlined"
                        fullWidth
                        sx={{mt: 2}}
                    />
                </FormControl>
                <FormControl fullWidth>
                    <TextField
                        required
                        label="Mobile Number"
                        id="phoneNumber"
                        name="phoneNumber"
                        type="number"
                        value={formik.values.phoneNumber}
                        onChange={formik.handleChange}
                        error={
                            formik.touched.phoneNumber && Boolean(formik.errors.phoneNumber)
                        }
                        helperText={
                            formik.touched.phoneNumber && formik.errors.phoneNumber
                        }
                        onBlur={formik.handleBlur}
                        variant="outlined"
                        fullWidth
                        sx={{mt: 2}}
                    />
                </FormControl>
                <FormControl fullWidth>
                    <TextField
                        required
                        label="Billing Address"
                        id="billingAddress"
                        name="billingAddress"
                        value={formik.values.billingAddress}
                        onChange={formik.handleChange}
                        error={
                            formik.touched.billingAddress &&
                            Boolean(formik.errors.billingAddress)
                        }
                        helperText={
                            formik.touched.billingAddress && formik.errors.billingAddress
                        }
                        onBlur={formik.handleBlur}
                        variant="outlined"
                        fullWidth
                        sx={{mt: 2}}
                    />
                </FormControl>
                <FormControl fullWidth>
                    <TextField
                        required
                        label="Shipping Address"
                        id="shippingAddress"
                        name="shippingAddress"
                        value={formik.values.shippingAddress}
                        onChange={formik.handleChange}
                        error={
                            formik.touched.shippingAddress &&
                            Boolean(formik.errors.shippingAddress)
                        }
                        helperText={
                            formik.touched.shippingAddress && formik.errors.shippingAddress
                        }
                        onBlur={formik.handleBlur}
                        variant="outlined"
                        fullWidth
                        sx={{mt: 2}}
                    />
                </FormControl>
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
                        sx={{mt: 2}}
                    />
                </FormControl>
                <FormControl fullWidth sx={{mt: 2}}>
                    <TextField
                        required
                        label="Password"
                        id="password"
                        name="password"
                        type="password"
                        value={formik.values.password}
                        onChange={handlePasswordChange}
                        error={formik.touched.password && Boolean(formik.errors.password)}
                        helperText={formik.touched.password && formik.errors.password}
                        onBlur={formik.handleBlur}
                        variant="outlined"
                        fullWidth
                    />
                    {passwordStrength !== null && (
                        <FormHelperText>
                            {passwordStrength === 0
                                ? "Weak"
                                : passwordStrength === 1
                                    ? "Fair"
                                    : passwordStrength === 2
                                        ? "Good"
                                        : "Strong"}
                        </FormHelperText>
                    )}
                </FormControl>

                <FormControl fullWidth sx={{mt: 2}}>
                    <TextField
                        required
                        label="Confirm Password"
                        id="confirmPassword"
                        name="confirmPassword"
                        type="password"
                        value={formik.values.confirmPassword}
                        onChange={formik.handleChange}
                        error={formik.touched.confirmPassword && Boolean(formik.errors.confirmPassword)}
                        helperText={formik.touched.confirmPassword && formik.errors.confirmPassword}
                        onBlur={formik.handleBlur}
                        variant="outlined"
                        fullWidth
                    />
                </FormControl>

                {/* File upload button */}
                <FormControl fullWidth sx={{mt: 2}}>
                    <input
                        accept=".jpg"
                        style={{display: "none"}}
                        id="raised-button-file"
                        type="file"
                        onChange={handleFileChange}
                    />
                    <label htmlFor="raised-button-file">
                        <Button variant="contained" component="span">
                            Upload Profile Picture
                        </Button>
                    </label>
                    {selectedFileName && (
                        <div style={{marginTop: "10px"}}>
                            <strong>Selected File:</strong> {selectedFileName}
                        </div>
                    )}
                </FormControl>
                {error && <p style={{color: 'red'}}>{error}</p>}
                <Button
                    variant="contained"
                    color="primary"
                    fullWidth
                    size="large"
                    type="submit"
                    sx={{mt: 4}}
                >
                    Register
                </Button>
                <Button
                    variant="outlined"
                    color="secondary"
                    fullWidth
                    size="large"
                    onClick={handleReset}
                    sx={{mt: 1}}
                    type="reset"
                >
                    Reset
                </Button>
            </form>
        </Container>
    );
}

export default Registration;