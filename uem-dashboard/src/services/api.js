import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8081",
});

export const getLatestMetrics = async (macId) => {
  const response = await API.get(
    `/device-metrics/latest/${macId}`
  );

  return response.data;
};