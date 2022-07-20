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
    const sample_type = datasample.sampleTypeName;
    const lab_number = datasample.labNumber;
    const date_sample_verified = datasample.dateSampleVerified;
    const time_sample_verified = datasample.timeSampleVerified;
    const date_result_reported = datasample.id ? datasample.date_result_reported : null ;
    const test_result = datasample.id ? datasample.comment_sample_reported : null ;
    const result_detail = datasample.id && datasample.reported_result ?  datasample.reported_result : null
    const lab_test_id = datasample.id ? datasample.id : 0 ;


    const getResults = useCallback(async () => {
        try {
            const response = await axios.get(`${url}laboratory/results/${lab_test_id}`, { headers: {"Authorization" : `Bearer ${token}`}});
            console.log("results ccxc", response.data);
            setCollectResult(response.data);
        } catch (e) {
           // toast.error("An error occurred while fetching sample results", {
             //   position: toast.POSITION.TOP_RIGHT
            //});
        }
    }, []);

    useEffect(() => {
        getResults();
    }, [getResults]);

  return (      
      <div>
              <Modal isOpen={props.modalstatus} toggle={props.togglestatus} className={props.className} size="lg">
                  <ModalHeader toggle={props.togglestatus}>Lab Test Order Detail</ModalHeader>
                      <ModalBody>
                          <Card>
                            <CardBody>
                                <Row style={{ marginTop: '20px'}}>
                                    <Col xs="12">
                                    <Alert color="success" style={{color:"#000" , fontWeight: 'bolder', }}>
                                     <p style={{marginTop: '.7rem' }}>Lab number: <span style={{ fontWeight: 'bolder'}}>{lab_number}</span>
                                         &nbsp;&nbsp;&nbsp;&nbsp;Sample type:
                                         <span style={{ fontWeight: 'bolder'}}>{" "}{sample_type}</span>
                                                 &nbsp;&nbsp;&nbsp;&nbsp; Date sample verified :
                                         <span style={{ fontWeight: 'bolder'}}>{" "}{date_sample_verified + "@" + time_sample_verified}</span>
                                     </p>

                                   </Alert>
                                    </Col>
                                    <Col xs="12">
                                        <h4>Results: </h4>
                                        <hr/>
                                    {
                                        !collectResult ? "" :

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
