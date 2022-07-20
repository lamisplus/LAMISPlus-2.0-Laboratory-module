import React, {useState} from 'react';
import { Modal, ModalHeader, ModalBody,Form,FormFeedback,Row,Col,
FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { connect } from 'react-redux';
import axios from "axios";
import "react-widgets/styles.css";
import { DateTimePicker } from 'react-widgets';
import Moment from 'moment';
import momentLocalizer from 'react-widgets-moment';
import moment from "moment";
import {token, url} from '../../../../api'
// import { useSelector, useDispatch } from 'react-redux';
// import { createCollectedSample, fetchFormById } from '../../../actions/laboratory'
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { Alert } from 'reactstrap';
import { Spinner } from 'reactstrap';
import { toast} from "react-toastify";
import { useHistory } from 'react-router-dom';

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

const ModalSampleResult = (props) => {
    const history = useHistory();
    const classes = useStyles()
    const datasample = props.datasample ? props.datasample : {};
    const sample_type = datasample.sampleTypeName;

    const lab_number = datasample.labNumber;
    const date_sample_collected = datasample.dateSampleCollected;
    const time_sample_collected = datasample.timeSampleCollected;
    const lab_test_id = datasample.id;

    const unit_measurement = datasample.id ? datasample.unitMeasurement : null ;
    const labId = datasample.id


    const [visible, setVisible] = useState(true);
    const onDismiss = () => setVisible(false);
    const [loading, setLoading] = useState(false)
    const [samples, setSamples] = useState({}) 
    const [otherfields, setOtherFields] = 
            useState({
            time_result_enetered:"",
            date_result_reported:"",
            resultReported:"",
            //test_result:"",
            date_asseyed:"",
            timeAssayed:"",
            //result_reported_by: ""
          }); 
    const [errors, setErrors] = useState({});

    const [sampleResult, setSampleResult] = useState({
          "dateAssayed": "",
          "dateResultReported": "",
          "id": 0,
          "resultReported": "",
          "testId": 0,
          "timeAssayed": moment(new Date()).format("HH:mm:ss"),
          "timeResultReported": moment(new Date()).format("HH:mm:ss"),
    });

  const handleOtherFieldInputChange = e => {
      setOtherFields ({ ...otherfields, [e.target.name]: e.target.value });
  }

  const validate = () => {
      let temp = { ...errors }
      ///temp.time_result_enetered = otherfields.time_result_enetered ? "" : "Date is required"
      temp.date_result_reported = otherfields.date_result_reported ? "" : "Date  is required."
      //temp.result_reported_by = otherfields.result_reported_by ? "" : "This filed is required."
      temp.date_asseyed = otherfields.date_asseyed ? "" : "This filed is required." 
      //temp.resultReported = otherfields.resultReported ? "" : "This filed is required."
      setErrors({
          ...temp
      })
      return Object.values(temp).every(x => x == "")
  }

    const saveSample = async (e) => {
        e.preventDefault()
        if(validate()){
              setLoading(true);

              const newDateReported = moment(otherfields.date_result_reported).format(
                                                  "YYYY-MM-DD"
                                              );
              const newTimeSampleEntered = moment(otherfields.date_result_reported).format("HH:mm:ss");

              sampleResult.dateAssayed = newDateReported;
              sampleResult.dateResultReported = newDateReported;
              sampleResult.testId = lab_test_id;
              sampleResult.timeAssayed = newTimeSampleEntered;
              sampleResult.timeResultReported = newTimeSampleEntered;
              sampleResult.resultReported = otherfields.resultReported;

              console.log("samples result", otherfields, sampleResult);

              await axios.post(`${url}laboratory/results`, sampleResult,
              { headers: {"Authorization" : `Bearer ${token}`}}).then(resp => {
                  console.log("sample result", resp);
                  setLoading(!true);
                   toast.success("Sample verified successfully!!", {
                      position: toast.POSITION.TOP_RIGHT
                  });

                  //localStorage.removeItem('labnumber');

                  //history.push('/result-reporting');
              });



      }
    }
    const textstyle = {
        fontSize: '14px',
        fontWeight: 'bolder'
      };

   function checklanumber(lab_num) {
          if (lab_num === "" || lab_num===null) {
              return (
                  <Alert color="danger" isOpen={visible} toggle={onDismiss}>
                      Please make sure you enter the sample lab number
                  </Alert>
              );
          }
      }
      
  return (
      <div >
          <Modal isOpen={props.modalstatus} toggle={props.togglestatus} className={props.className} size="lg">
              <Form onSubmit={saveSample}>
                  <ModalHeader toggle={props.togglestatus}>Result Reporting</ModalHeader>
                      <ModalBody>
                            {/*{checklanumber(lab_number)}*/}
                          <Card>
                              <CardBody>
                                  <Row style={{ marginTop: '20px'}}>
                                      <Col md="12" >
                                          <Alert color="primary" style={{color:"#000" , fontWeight: 'bolder', }}>
                                          <p style={{marginTop: '.7rem' }}>Lab number: <span style={{ fontWeight: 'bolder'}}>{lab_number}</span>
                                              &nbsp;&nbsp;&nbsp;&nbsp;Sample type:
                                              <span style={{ fontWeight: 'bolder'}}>{" "}{sample_type}</span>
                                                      &nbsp;&nbsp;&nbsp;&nbsp; Date sample collected :
                                              <span style={{ fontWeight: 'bolder'}}>{" "}{date_sample_collected + "@" + time_sample_collected}</span>
                                          </p>

                                        </Alert>
                                            <br />
                                      </Col>

                                      <Col xs="6">
                                          Date Assayed
                                            <br/>
                                            <DateTimePicker time={false} name="date_asseyed"  id="date_asseyed"  
                                              onChange={value1 =>
                                                setOtherFields({ ...otherfields, date_asseyed: value1 })
                                              }
                                            /> 
                                      </Col>
                                      <Col xs="6">
                                          Date Reported
                                              <br/>
                                              <DateTimePicker time={false} name="date_result_reported"  id="date_result_reported"  
                                                onChange={value1 =>
                                                  setOtherFields({ ...otherfields, date_result_reported: value1 })
                                                }
                                              />            
                                      </Col>
                                      <Col md={6}>
                                          <FormGroup>
                                            <Label for=''>Time Assayed</Label>

                                             <Input
                                                type="text"
                                                name="time_result_assayed"
                                                id="time_result_assayed"
                                                onChange={value1 =>
                                                    setOtherFields({ ...otherfields, timeAssayed: value1 })
                                                }
                                                value={sampleResult.timeAssayed}
                                            />

                                              {/*  {errors.time_sample_verified !="" ? (
                                                  <span className={classes.error}>{errors.time_sample_verified}</span>
                                                  ) : "" } */}
                                          </FormGroup>
                                      </Col>
                                        <Col md={6}>
                                            <FormGroup>
                                              <Label for=''>Time Reported</Label>

                                               <Input
                                                  type="text"
                                                  name="time_result_reported"
                                                  id="time_result_reported"
                                                  onChange={value1 =>
                                                      setOtherFields({ ...otherfields, timeResultReported: value1 })
                                                  }
                                                  value={sampleResult.timeResultReported}
                                              />

                                                {/*  {errors.time_sample_verified !="" ? (
                                                    <span className={classes.error}>{errors.time_sample_verified}</span>
                                                    ) : "" } */}
                                            </FormGroup>
                                        </Col>
                                  </Row>
                                  <Row>             
                                     <Col md={12}>
                                       <FormGroup>
                                         <Label for='resultReported'>Result Report</Label>
                                         <Input
                                           type='textarea'
                                           name='resultReported'
                                           id='resultReported'
                                           style={{ minHeight: 100, fontSize: 14 }}
                                           onChange={handleOtherFieldInputChange}
                                           >
                                           </Input>
                                       </FormGroup>
                                     </Col>

                                </Row>
                                    <br/>
                                    {loading ? <Spinner /> : ""}
                                    <br/>
                                         {lab_number && lab_number !== null ? (
                                           <MatButton
                                               type="submit"
                                               variant="contained"
                                               color="primary"
                                               className={classes.button}
                                               startIcon={<SaveIcon />}
                                               disabled={loading}
                                           >
                                               Save
                                           </MatButton>
                                       ) : (
                                           <MatButton
                                               type="submit"
                                               variant="contained"
                                               color="primary"
                                               className={classes.button}
                                               startIcon={<SaveIcon />}
                                               disabled="true"
                                           >
                                               Save
                                           </MatButton>
                                       )}
                                       <MatButton
                                           variant="contained"
                                           color="default"
                                           onClick={props.togglestatus}
                                           className={classes.button}
                                           startIcon={<CancelIcon />}
                                       >
                                           Cancel
                                       </MatButton>
                                </CardBody>
                            </Card>
                        </ModalBody>
              </Form>
          </Modal>
      </div>
  );
}

export default ModalSampleResult;
