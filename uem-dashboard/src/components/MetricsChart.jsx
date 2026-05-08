import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer,
    Legend
} from "recharts";

function MetricsChart({ data }) {

    return (
        <div className="chart-container">

            <h2>Live Device Metrics</h2>

            <ResponsiveContainer width="100%" height={500}>

                <LineChart data={data}>

                    <CartesianGrid strokeDasharray="3 3" />

                    <XAxis dataKey="time" />

                    <YAxis domain={[0, 100]} />

                    <Tooltip />

                    <Legend />

                    <Line
                        type="monotone"
                        dataKey="cpu"
                        name="CPU %"
                    />

                    <Line
                        type="monotone"
                        dataKey="ram"
                        name="RAM %"
                    />

                    <Line
                        type="monotone"
                        dataKey="disk"
                        name="Disk %"
                    />

                    <Line
                        type="monotone"
                        dataKey="battery"
                        name="Battery %"
                    />

                </LineChart>

            </ResponsiveContainer>

        </div>
    );
}

export default MetricsChart;