import axios from "axios";

const jwt =
  "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuaWtAZ21haWwuY29tIiwiaWF0IjoxNjU1OTMyMTY4LCJleHAiOjE2ODU5MzIxNjh9.STzNAXh9Ehs_TZBBOe3qApksL1EHHJcw4dpIdaIG2wA";

const expenseTrackerApi = axios.create({
  baseURL: process.env.REACT_APP_BACKEND_API,
  headers: {
    Authorization: `Bearer ${jwt}`,
  },
});

export default expenseTrackerApi;
