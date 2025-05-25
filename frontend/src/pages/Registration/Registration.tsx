
import { Tabs } from "antd";
import TrainerForm from "./component/TrainerForm";
import ClientForm from "./component/ClientForm";

const Registration = () => {
  return (
    <>
      <Tabs
        defaultActiveKey="client"
        centered
        items={[
          {
            label: 'Client',
            key: 'client',
            children: <ClientForm />
          },
          {
            label: 'Trainer',
            key: 'trainer',
            children: <TrainerForm />,
          },
        ]}
      />
    </>
  )
};

export default Registration;
