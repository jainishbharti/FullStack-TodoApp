import React from "react";
import TodoTimeline from "./TodoTimeline";
import { Box, Typography } from "@mui/material";
import { styled } from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid";
import TodoStepper from "./Stepper";

export const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: "center",
  color: theme.palette.text.secondary,
}));

export const DemoTodo = () => {
  return (
    <>
      <Grid container spacing={2} pb={6} mt={6}>
        <Grid item xs={12}>
          <Item>
            <Box p={6}>
              <Typography variant="h2" align="left">
                What's on your mind? TODAY
              </Typography>
              <div data-testid="todostepper"><TodoStepper /></div>
            </Box>
          </Item>
        </Grid>
        <Grid item xs={8}>
          <Item>
            <Box>
              <Typography sx={{ margin: "1.5rem" }} component="h3">
                Timeline Example
              </Typography>
              <div data-testid="timeline"><TodoTimeline /></div>
            </Box>
          </Item>
        </Grid>
        <Grid item xs={4}>
          <Item>
            <Typography
              sx={{ color: "warning", fontWeight: "550" }}
              component="h4"
            >
              Why we need it?
            </Typography>
          </Item>
        </Grid>
        <Grid item xs={4}>
          <Item>
            <Typography
              sx={{ color: "success", fontWeight: "550" }}
              component="h4"
            >
              It makes things more handy! We don't need to do it on Paper.
            </Typography>
          </Item>
        </Grid>
        <Grid item xs={8}>
          <Item>
            <Box sx={{ margin: "auto" }}>
              <img
                src="https://img.freepik.com/free-vector/list-concept-illustration_114360-2498.jpg?w=2000"
                alt="todo illustration"
                width="100%"
              />
            </Box>
          </Item>
        </Grid>
      </Grid>
    </>
  );
};
