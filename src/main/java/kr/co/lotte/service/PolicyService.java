package kr.co.lotte.service;

import kr.co.lotte.entity.Policy;
import kr.co.lotte.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;

    public Policy buyerPolicy(Long id) {
        Optional<Policy> policy = policyRepository.findById(id);
        return policy.get();
    }
}
