import React, { useState, useCallback, useEffect }   from 'react';
import {
    Modal, ModalHeader, ModalBody, Row, Col, FormGroup, Label, Card, CardBody, Alert
} from 'reactstrap';
import MatButton from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import CancelIcon from '@material-ui/icons/Cancel';
import { Badge } from 'reactstrap';
import ReactHtmlParser from 'react-html-parser'
import Divider from '@material-ui/core/Divider';
import axios from "axios";
import { toast } from 'react-toastify';
import {token, url} from '../../../../api'

const useStyles = makeStyles(theme => ({
    card: {
        margin: theme.spacing(20),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center'
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3)
    },
    submit: {
        margin: theme.spacing(3, 0, 2)
    },
    cardBottom: {
        marginBottom: 20
    },
    Select: {
        height: 45,
        width: 350
    },
    button: {
        margin: theme.spacing(1)
    },

    root: {
        '& > *': {
            margin: theme.spacing(1)
        }
    },
    input: {
        display: 'none'
    } 
}))

const ModalViewResult = (props) => {

    const [collectResult, setCollectResult] = useState([])

    const classes = useStyles()
    const datasample = props.datasample ? props.datasample : {};
    //console.log(datasample)
    const sample_type = datasample.sampleTypeName;
    const lab_number = datasample.labNumber;
    const date_sample_verified = datasample.dateSampleVerified;
    const time_sample_verified = datasample.timeSampleVerified;
    const date_result_reported = datasample.id ? datasample.date_result_reported : null ;
    const test_result = datasample.id ? datasample.comment_sample_reported : null ;
    const result_detail = datasample.id && datasample.reported_result ?  datasample.reported_result : null
    const sample_id = datasample.id ? datasample.id : 0 ;

    const [sampleResults, setSampleResults] = useState({
         id: 1,
         uuid: "5fb73629-64c6-406f-8ce4-57b01b98dc7c",
         sampleID: "19",
         pcrLabSampleNumber: "BMSH/BMMC/22/0089",
         visitDate: "2022-09-09",
         dateSampleReceivedAtPcrLab: "2022-09-09",
         resultDate: "2022-09-09",
         testResult: "67854",
         assayDate: "2022-09-09",
         approvalDate: "2022-09-09",
         dateResultDispatched: "2022-09-09",
         sampleStatus: "Completed",
         sampleTestable: "true"
       })

     const loadSampleResults = useCallback(async () => {
       try {
            if (typeof datasample.id !== "undefined") {
                const response = await axios.get(`${url}lims/results/${datasample.id}`, { headers: {"Authorization" : `Bearer ${token}`} });
                console.log("sample results",response);
            }

        } catch (e) {
            toast.error("An error occurred while fetching lab", {
                position: toast.POSITION.TOP_RIGHT
            });
        }
    }, []);

    const getResults = useCallback(async () => {
        try {
            const response = await axios.get(`${url}laboratory/results/${sample_id}`, { headers: {"Authorization" : `Bearer ${token}`}});
            //console.log("results ccxc", response);
            setCollectResult(response.data);
        } catch (e) {
           // toast.error("An error occurred while fetching sample results", {
             //   position: toast.POSITION.TOP_RIGHT
            //});
        }
    }, []);

    useEffect(() => {
        loadSampleResults();
        getResults();
    }, [getResults, loadSampleResults]);

  return (      
      <div>
              <Modal isOpen={props.modalstatus} toggle={props.togglestatus} className={props.className} size="lg">
                  <ModalHeader toggle={props.togglestatus}>Lab Test Result Details</ModalHeader>
                      <ModalBody>
                          <Card>
                            <CardBody>
                                <Row style={{ marginTop: '20px'}}>
                                    <Col xs="12">
                                    <Alert color="success" style={{color:"#000" , fontWeight: 'bolder', }}>
                                     <p style={{marginTop: '.7rem' }}>
                                        Lab number: &nbsp;&nbsp; <span style={{ fontWeight: 'bolder'}}>{ lab_number}</span>
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         Sample ID: &nbsp;&nbsp; <span style={{ fontWeight: 'bolder'}}>{" "}{ sample_id}</span>
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                         Sample type: &nbsp;&nbsp;
                                         <span style={{ fontWeight: 'bolder'}}>{" "}{ sample_type}</span>
                                          <br/>
                                         Date sample verified : &nbsp;&nbsp;
                                         <span style={{ fontWeight: 'bolder'}}>{" "}{ date_sample_verified + " " + time_sample_verified}</span>
                                     </p>

                                   </Alert>
                                    </Col>
                                    <Col xs="12">
                                        <h4>Results: </h4>
                                        <hr/>
                                        {datasample.labTestName === "Viral Load" ?
                                        <>
                                            <Row>
                                                <Col xs="4">
                                                    <span style={{ fontWeight: 'bold'}}>PCR Lab Sample Number </span>: { sampleResults.pcrLabSampleNumber }
                                                    <br/>
                                                </Col>
                                                <Col xs="4">
                                                    <span style={{ fontWeight: 'bold'}}>Date Received at PCR Lab</span>: { sampleResults.dateSampleReceivedAtPcrLab }
                                                    <br/>
                                                </Col>
                                                 <Col xs="4">
                                                    <span style={{ fontWeight: 'bold'}}>Assay date</span>: { sampleResults.assayDate }
                                                    <br/>
                                                </Col>
                                            </Row>
                                            <br/>
                                             <Row>
                                                <Col xs="4">
                                                    <span style={{ fontWeight: 'bold'}}>Sample Test Status</span>: <Badge color="success"> {ReactHtmlParser(sampleResults.sampleStatus)}</Badge>
                                                    <br/>
                                                </Col>
                                                <Col xs="4">
                                                    <span style={{ fontWeight: 'bold'}}>Result Date</span>: { sampleResults.resultDate }
                                                    <br/>
                                                </Col>
                                                 <Col xs="4">
                                                    <span style={{ fontWeight: 'bold'}}>Sample Result</span>: { sampleResults.testResult }
                                                    <br/>
                                                </Col>
                                            </Row>
                                            <hr />
                                            </>
                                            :  !collectResult ? "No Result Available" :

                                              <Row >
                                                  <Col xs="4">
                                                  <span style={{ fontWeight: 'bold'}}>Date Assayed </span>: {collectResult.dateAssayed + " " + collectResult.timeAssayed }
                                                  <br/>
                                                  </Col>
                                                  <br/>
                                                  <Col xs="4">
                                                      <span style={{ fontWeight: 'bold'}}>Date Reported </span>: {collectResult.dateResultReported + " " + collectResult.timeResultReported}
                                                      <br/>
                                                  </Col>

                                                  <Col xs="4">
                                                      {/*<span style={{ fontWeight: 'bold'}}> Result </span>:*/}
                                                      <Badge  color="info"> {ReactHtmlParser("Result Available")}</Badge>
                                                  </Col>
                                                                 {/* <Col xs="6">
                                                                      <span style={{ fontWeight: 'bold'}}> Unit Measurement </span>: {unit_measurement}
                                                                  </Col> */}

                                                  <Col xs="12">
                                                  <br />
                                                      <span style={{ fontWeight: 'bold'}}> Notes </span>: {ReactHtmlParser(collectResult.resultReported)}
                                                      <Divider  />
                                                  </Col>
                                              </Row>
                                        }
                                    </Col>
                    
                                </Row>
                            <br/>
                          
                            <MatButton
                              variant='contained'
                              color='default'
                              onClick={props.togglestatus}
                              className={classes.button}
                              startIcon={<CancelIcon />}
                            >
                              Cancel
                            </MatButton>
                      </CardBody>
                </Card>
          </ModalBody>
      </Modal>
    </div>
  );
}

export default ModalViewResult;
