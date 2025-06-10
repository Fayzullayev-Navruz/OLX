package uz.pdp.securitytest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.*;
import uz.pdp.securitytest.payload.ProductDTO;
import uz.pdp.securitytest.payload.UserMessageDto;
import uz.pdp.securitytest.repository.ManagerRepository;
import uz.pdp.securitytest.repository.ProductRepository;
import uz.pdp.securitytest.repository.UncheckADDSRepository;
import uz.pdp.securitytest.service.UncheckedADDSService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/uncheck")
@CrossOrigin(origins = "http://localhost:63342")
public class UncheckADDSController {
    private final UncheckedADDSService uncheckedADDSService;

    public UncheckADDSController(UncheckedADDSService uncheckedADDSService) {

        this.uncheckedADDSService = uncheckedADDSService;
    }

    @GetMapping
    public ApiResponse getOwnWork() {
        List<ProductDTO> ownWork = uncheckedADDSService.getOwnWork();
        return new ApiResponse<>(HttpStatus.OK.toString(), ownWork);
    }
    @GetMapping("/checked")
    public ApiResponse getOwnCheckedWork() {
        List<ProductDTO> ownCheckedWork = uncheckedADDSService.getOwnCheckedWork();
        return new ApiResponse<>(HttpStatus.OK.toString(), ownCheckedWork);
    }

    @PostMapping
    public ApiResponse add(@RequestBody ProductDTO productDTO) {
        ProductDTO productDTO1 = uncheckedADDSService.add(productDTO);
        return new ApiResponse<>(HttpStatus.OK.toString(),productDTO1);
    }



}
