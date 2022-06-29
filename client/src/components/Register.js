import React, { useState } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import axios from "axios";
import * as Yup from "yup";
import { Link } from "react-router-dom";
import { Typography } from "@mui/material";

const validationSchema = Yup.object({
  name: Yup.string().required('Required'),
  email: Yup.string().email("Invalid email format").required('Required'),
  password: Yup.string()
    .required('Required')
    .min(8, "Password is too short - should be 8 chars minimum.")
    .matches(/[a-zA-Z]/, "Password can only contain Latin letters."),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref("password"), ""], "Passwords must match")
    .required("Required"),
});

const initialValues = {
  name: "",
  email: "",
  password: "",
  confirmPassword: "",
};

const Register = () => {
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  return (
    <div>
      <h1 className="heading">Register Yourself</h1>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={async (values, { setSubmitting, resetForm }) => {
          const { confirmPassword, ...rest } = values;
          try {
            const result = await axios({
              method: "POST",
              url: "/users/register",
              data: rest,
              headers: {
                "Content-Type": "application/json",
              },
            });

            if (result.status === 201) {
              setMessage("User created successfully!");
              setError("");
              const user = await result.data;
              if (user) console.log("User created successfully!");
            }
          } catch (err) {
            console.log(err);
            setError("Email already exists");
          }

          setSubmitting(false);
          resetForm();
        }}
      >
        {({ isSubmitting }) => (
          <Form className="form">
            {message !== "" ? <span className="success-msg"><strong>{message}</strong></span> : ""}
            <Field
              as={TextField}
              type="text"
              name="name"
              label="Name"
              variant="outlined"
              sx={{
                label: { color: "black" },
                width: 300,
                marginBottom: "1rem",
              }}
            />
            <ErrorMessage name="lastName" component="div" className="err-msg" />
            <Field
              as={TextField}
              type="email"
              name="email"
              label="Email"
              variant="outlined"
              sx={{
                label: { color: "black" },
                width: 300,
                marginBottom: "1rem",
              }}
            />
            <ErrorMessage name="email" component="div" className="err-msg" />
            <Field
              as={TextField}
              type="password"
              name="password"
              label="Password"
              variant="outlined"
              sx={{
                label: { color: "black" },
                width: 300,
                marginBottom: "1rem",
              }}
            />
            <ErrorMessage name="password" component="div" className="err-msg" />

            <Field
              as={TextField}
              type="password"
              name="confirmPassword"
              label="Confirm Password"
              variant="outlined"
              sx={{
                label: { color: "black" },
                width: 300,
                marginBottom: "1rem",
              }}
            />
            <ErrorMessage
              name="confirmPassword"
              component="div"
              className="err-msg"
            />
            {error && <p className="err-msg">{error}</p>}

            <Button
              role="registerButton"
              type="submit"
              disabled={isSubmitting}
              variant="contained"
              sx={{ marginTop: "1rem", marginBottom: "1rem" }}
            >
              Submit
            </Button>
          </Form>
        )}
      </Formik>
      <Typography align="center" sx={{ color: "black" }}>
        Already have an account?{" "}
        <Link to="/login" className="link">
          Login here
        </Link>
      </Typography>
    </div>
  );
};

export default Register;
