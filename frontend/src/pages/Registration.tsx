
import { Tabs } from "antd";
import TrainerForm from "../components/Forms/TrainerForm";
import ClientForm from "../components/Forms/ClientForm";

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
