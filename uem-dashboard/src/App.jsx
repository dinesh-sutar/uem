import { useEffect, useState } from "react";

import { getLatestMetrics } from "./services/api";

import DeviceCard from "./components/DeviceCard";
import MetricsChart from "./components/MetricsChart";

import "./index.css";

function App() {

  const [macId, setMacId] = useState("");
  const [currentData, setCurrentData] = useState(null);

  const [historyData, setHistoryData] = useState([]);

  const [monitoring, setMonitoring] = useState(false);

  // Load previous session history
  useEffect(() => {

    const stored = sessionStorage.getItem("metrics-history");

    if (stored) {
      setHistoryData(JSON.parse(stored));
    }

  }, []);

  // Poll every 17 seconds
  useEffect(() => {

    if (!monitoring || !macId) return;

    fetchMetrics();

    const interval = setInterval(() => {

      fetchMetrics();

    }, 2000);

    return () => clearInterval(interval);

  }, [monitoring, macId]);

  const fetchMetrics = async () => {

    try {

      const response = await getLatestMetrics(macId);

      setCurrentData(response);

      const newEntry = {

        time: new Date().toLocaleTimeString(),

        cpu: response.cpuUsage,

        ram:
          (
            (
              (response.totalMemory - response.availableMemory)
              / response.totalMemory
            ) * 100
          ).toFixed(2),

        disk:
          (
            (
              (response.totalDisk - response.freeDisk)
              / response.totalDisk
            ) * 100
          ).toFixed(2),

        battery: response.batteryLevel,

        sent: response.bytesSent,
        received: response.bytesReceived
      };

      setHistoryData((prevHistory) => {

        const updatedHistory = [...prevHistory, newEntry];

        // Keep only latest 50 entries
        const limitedHistory = updatedHistory.slice(-50);

        sessionStorage.setItem(
          "metrics-history",
          JSON.stringify(limitedHistory)
        );

        return limitedHistory;
      });

    } catch (error) {

      console.error(error);

      alert("Device not found");
    }
  };

  const startMonitoring = () => {

    sessionStorage.removeItem("metrics-history");

    setHistoryData([]);

    setMonitoring(true);
  };

  return (
    <div className="container">

      <h1>UEM Live Monitoring Dashboard</h1>

      <div className="search-box">

        <input
          type="text"
          placeholder="Enter Device MAC ID"
          value={macId}
          onChange={(e) => setMacId(e.target.value)}
        />

        <button onClick={startMonitoring}>
          Start Monitoring
        </button>

      </div>

      {currentData && (
        <DeviceCard data={currentData} />
      )}

      {historyData.length > 0 && (
        <MetricsChart data={historyData} />
      )}

    </div>
  );
}

export default App;