import React, {useState, Fragment, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import { Row, Col, Card,  Tab, Tabs, } from "react-bootstrap";
import LabTestOrderSearch from './Laboratory/Testorders/LabTestOrderSearch';
import LabTestResultSearch from './Laboratory/TestResult/LabTestResultSearch';
import LabTestVerifySampleSearch from './Laboratory/Sampleverifications/LabTestVerifySampleSearch';
import CheckInPatients from './CheckInPatients/Index';
import {labObj} from './LabObj'

const divStyle = {
  borderRadius: "2px",
  fontSize: 14,
};

const Home = (props) => {
    const [key, setKey] = useState('collection');
    //const urlIndex = getQueryParams("tab", props.location.search); 
    const urlTabs = props.location && props.location.state ? props.location.state : null ;
    console.log(props)
  useEffect ( () => {
    switch(urlTabs){  
      case "collect-sample": return setKey('collection')
      case "verification": return setKey('verification')
      case "result": return setKey('result')
      
      default: return setKey('collection')
    }
  }, [urlTabs]);


  return (
    <Fragment>
     
      <Row>
       
        <Col xl={12}>
          <Card style={divStyle}>
            
            <Card.Body>
              {/* <!-- Nav tabs --> */}
              <div className="custom-tab-1">
                <Tabs
                    id="controlled-tab-example"
                    activeKey={key}
                    onSelect={(k) => setKey(k)}
                    className="mb-3"
                    >
                    {/* <Tab eventKey="home" title="Checked In Patients">                   
                      <CheckInPatients labObj={labObj} />
                    </Tab> */}
                    <Tab eventKey="collection" title="Test Orders">                   
                      <LabTestOrderSearch labObj={labObj} />
                    </Tab>
                    <Tab eventKey="verification" title="Sample Verification">                   
                      <LabTestVerifySampleSearch  labObj={labObj}/>
                    </Tab>
                    <Tab eventKey="result" title="Result Reporting">                   
                      <LabTestResultSearch labObj={labObj}/>
                    </Tab>
                    
                    </Tabs>


              </div>
            </Card.Body>
          </Card>
        </Col>
        
      </Row>
    </Fragment>
  );
};

export default Home;
