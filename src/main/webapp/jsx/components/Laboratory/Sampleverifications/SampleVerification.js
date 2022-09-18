import React, { useState, useEffect } from "react";
import {Modal,ModalHeader, ModalBody,Form,FormFeedback,Row,Alert,Col,Input,FormGroup,Label,Card,CardBody,} from "reactstrap";
import axios from "axios";
import MatButton from "@material-ui/core/Button";
import { makeStyles } from "@material-ui/core/styles";
import SaveIcon from "@material-ui/icons/Save";
import CancelIcon from "@material-ui/icons/Cancel";
import { connect } from "react-redux";
import Chip from "@material-ui/core/Chip";
import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/styles.css";
import { DateTimePicker } from "react-widgets";
import Moment from "moment";
import momentLocalizer from "react-widgets-moment";
import moment from "moment";
import { token, url } from "../../../../api";
import { Spinner } from "reactstrap";
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
        border:'2px solid #014d88',
        borderRadius:'0px',
        fontSize:'16px',
        color:'#000'
    },
    error: {
        color: "#f85032",
        fontSize: "11px",
    },
    success: {
        color: "#4BB543 ",
        fontSize: "11px",
    },
    inputGroupText:{
        backgroundColor:'#014d88',
        fontWeight:"bolder",
        color:'#fff',
        borderRadius:'0px'
    },
    label:{
        fontSize:'16px',
        color:'rgb(153, 46, 98)',
        fontWeight:'600'
    }
}))

const ModalVerifySample = (props) => {
    const history = useHistory();
    const classes = useStyles()
    const datasample = props.datasample && props.datasample!==null ? props.datasample : {};
    //console.log('modal', datasample)

    const sample_type = datasample.sampleTypeName;
    const lab_number = datasample.labNumber;
    const date_sample_collected = datasample.dateSampleCollected;
    const time_sample_collected = datasample.timeSampleCollected;
    const lab_test_id = datasample.id;

    const sampleId = datasample.id;

    const [loading, setLoading] = useState(false);
    const [visible, setVisible] = useState(true);
    const onDismiss = () => setVisible(false);
    const [samples, setSamples] = useState({});
    const [optionsample, setOptionsample] = useState([]);
    const [saveButtonStatus, setSaveButtonStatus] = useState(false);
    const [otherFields, setotherFields] = useState({
        date_sample_verified:"",
        sample_verified_by:"",
        sample_priority:"",
        time_sample_verified:"",
        comment:"",
        verification_status:"",
        comment_sample_verified:""
    });

    const [errors, setErrors] = useState({});

    const [samplesVerified, setSamplesVerified] = useState({
          "commentSampleVerified": "",
          "dateSampleVerified": moment(new Date()).format("HH:mm:ss"),
          "sampleAccepted": "string",
          "sampleVerifiedBy": "string",
          "testId": 0,
          "timeSampleVerified": ""
    });

    useEffect(() => {
        async function getCharacters() {
            try {
            } catch (error) {
            }
        }
    }, []);

    const handleInputChangeSample = (e) => {
        setSamples({ ...samples, [e.target.name]: e.target.value });
    };

    const handleOtherFieldInputChange = e => {
      setotherFields ({ ...otherFields, [e.target.name]: e.target.value });
    }

    /*****  Validation */
   const validate = () => {
         let temp = { ...errors }
         temp.date_sample_verified = otherFields.date_sample_verified ? "" : "Date is required"
         //temp.time_sample_verified = otherFields.time_sample_verified ? "" : "Time  is required."
         //temp.sample_verified_by = otherFields.sample_verified_by ? "" : "This filed is required."
         temp.comment = otherFields.comment ? "" : "Comment is required."
         //temp.verification_status = otherFields.verification_status ? "" : "This filed is required."
         setErrors({
             ...temp
         })
         return Object.values(temp).every(x => x == "")
       }

    const saveSample = async (e) => {
        e.preventDefault();

         try {

             if (validate()) {
                setLoading(true);

                const newDatenow = moment(otherFields.date_sample_verified).format(
                                    "YYYY-MM-DD"
                                );
                const newTimeSampleVerified = moment(otherFields.date_sample_verified).format("HH:mm:ss");

                samplesVerified.commentSampleVerified = otherFields.comment;
                samplesVerified.dateSampleVerified = newDatenow;
                samplesVerified.sampleAccepted = otherFields.verification_status;
                samplesVerified.sampleVerifiedBy = otherFields.sample_verified_by;
                samplesVerified.testId = lab_test_id;
                samplesVerified.timeSampleVerified = newTimeSampleVerified;

                await axios.post(`${url}laboratory/verified-samples/${lab_test_id}`, samplesVerified,
                { headers: {"Authorization" : `Bearer ${token}`}}).then(resp => {
                    console.log("sample verify", resp);
                    setLoading(!true);
                     toast.success("Sample verified successfully!!", {
                        position: toast.POSITION.TOP_RIGHT
                    });
                });

                props.togglestatus()
                props.handDataReload()
            }

         } catch (e) {

            toast.error("An error occurred during sample collection", {
                 position: toast.POSITION.TOP_RIGHT
             });
         }
    };

    function checklanumber(lab_num) {
        if (lab_num === "" || lab_num===null) {
            return (
                <Alert color="danger" isOpen={visible} toggle={onDismiss}>
                    Please make sure you enter a lab number
                </Alert>
            );
        }
    }

    return (
        <div >
            <Card >
                <CardBody>
                    <Modal isOpen={props.modalstatus} toggle={props.togglestatus} className={props.className} size="lg">
                        <Form onSubmit={saveSample}>
                            <ModalHeader toggle={props.togglestatus}>Verify Sample </ModalHeader>
                            <ModalBody>
                                {/**{checklanumber(lab_number)}*/}
                                <Card >
                                    <CardBody>
                                        <Row >
                                            <Col md={12} >
                                               <Alert color="primary" style={{color:"#000" , fontWeight: 'bolder', }}>
                                                <p style={{marginTop: '.7rem' }}>Lab number: <span style={{ fontWeight: 'bolder'}}>{lab_number}</span>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;Sample type:
                                                    <span style={{ fontWeight: 'bolder'}}>{" "}{sample_type}</span>
                                                            &nbsp;&nbsp;&nbsp;&nbsp; Date sample collected :
                                                    <span style={{ fontWeight: 'bolder'}}>{" "}{date_sample_collected + " " + time_sample_collected}</span>
                                                </p>

                                              </Alert>
                                        </Col>
                                            <Col md={6}>
                                              <FormGroup>
                                                <Label for='maritalStatus' className={classes.label}>Date Verified</Label>
                                                <DateTimePicker
                                                    className={classes.input}
                                                    time={false}
                                                    name="date_sample_verified"
                                                    id="date_sample_verified"
                                                    onChange={value1 =>
                                                      setotherFields({ ...otherFields, date_sample_verified: value1 })
                                                    }
                                                />
                                                  {errors.time_sample_transfered !="" ? (
                                                    <span className={classes.error}>{errors.time_sample_transfered}</span>
                                                  ) : "" }
                                              </FormGroup>
                                            </Col>
                                            <Col md={6}>
                                              <FormGroup>
                                                <Label for='' className={classes.label}>Time Verified</Label>

                                                 <Input
                                                    className={classes.input}
                                                    type="text"
                                                    name="time_sample_verified"
                                                    id="time_sample_verified"
                                                    onChange={value1 =>
                                                        setotherFields({ ...otherFields, time_sample_verified: value1 })
                                                    }
                                                    value={samplesVerified.dateSampleVerified}
                                                />

                                                  {/*  {errors.time_sample_verified !="" ? (
                                                      <span className={classes.error}>{errors.time_sample_verified}</span>
                                                      ) : "" } */}
                                              </FormGroup>
                                              </Col>
                                          <Col md={6}>
                                              <FormGroup>
                                                <Label for="exampleSelect" className={classes.label}>Confirm Sample</Label>
                                                <Input type="select" name="verification_status" id="verification_status"
                                                  value={otherFields.verification_status} className={classes.input}
                                                  {...(errors.verification_status && { invalid: true})}
                                                onChange={handleOtherFieldInputChange}>
                                                  <option>Select</option>
                                                  <option value="Valid">Sample Valid </option>
                                                  <option value="Rejected">Sample Rejected</option>

                                                </Input>
                                                <FormFeedback>{errors.verification_status}</FormFeedback>
                                              </FormGroup>
                                              </Col>
                                              <Col md={6}>
                                                <FormGroup>
                                                    <Label for="sample_verified_by" className={classes.label}>Verify by </Label>

                                                        <Input
                                                        className={classes.input}
                                                          type="select"
                                                          name="sample_verified_by"
                                                          id="sample_verified_by"
                                                          vaule={otherFields.sample_verified_by}
                                                          onChange={handleOtherFieldInputChange}
                                                          {...(errors.sample_verified_by && { invalid: true})}
                                                        >
                                                          <option value=""> </option>
                                                           <option value="Data clerks"> Data clerks </option>
                                                           <option value="Admin"> Admin </option>
                                                      </Input>
                                                          <FormFeedback>{errors.sample_verified_by}</FormFeedback>
                                                </FormGroup>
                                            </Col>
                                              <Col md={12}>
                                              <FormGroup>
                                                <Label for='maritalStatus' className={classes.label}>Note</Label>
                                                <Input
                                                className={classes.input}
                                                  type='textarea'
                                                  name='comment'
                                                  id='comment'
                                                  style={{ minHeight: 100, fontSize: 14 }}
                                                  onChange={handleOtherFieldInputChange}
                                                  value = {otherFields.comment}
                                                  {...(errors.comment && { invalid: true})}
                                                  >
                                                  </Input>
                                                  <FormFeedback>{errors.comment}</FormFeedback>

                                              </FormGroup>
                                            </Col>

                                            <br />
                                            <br />
                                            <Col md={12}>
                                                {loading ? (
                                                    <>
                                                        <Spinner /> <p> &nbsp;&nbsp;Processing...</p>
                                                    </>
                                                ) : (
                                                    ""
                                                )}
                                            </Col>
                                        </Row>
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
                </CardBody>
            </Card>
        </div>
    );
};

export default ModalVerifySample;
