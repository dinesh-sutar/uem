function DeviceCard({ data }) {
    return (
        <div className="card">
            <h2>{data.deviceName}</h2>

            <p><strong>MAC:</strong> {data.deviceMacId}</p>
            <p><strong>OS:</strong> {data.osName}</p>
            <p><strong>IP:</strong> {data.ipAddress}</p>
            <p><strong>CPU:</strong> {data.cpuUsage}%</p>
            <p><strong>Battery:</strong> {data.batteryLevel}%</p>
        </div>
    );
}

export default DeviceCard;