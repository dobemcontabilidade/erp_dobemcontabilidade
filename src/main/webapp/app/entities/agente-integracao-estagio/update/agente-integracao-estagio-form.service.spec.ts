import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../agente-integracao-estagio.test-samples';

import { AgenteIntegracaoEstagioFormService } from './agente-integracao-estagio-form.service';

describe('AgenteIntegracaoEstagio Form Service', () => {
  let service: AgenteIntegracaoEstagioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgenteIntegracaoEstagioFormService);
  });

  describe('Service methods', () => {
    describe('createAgenteIntegracaoEstagioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAgenteIntegracaoEstagioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cnpj: expect.any(Object),
            razaoSocial: expect.any(Object),
            coordenador: expect.any(Object),
            cpfCoordenadorEstagio: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            complemento: expect.any(Object),
            bairro: expect.any(Object),
            cep: expect.any(Object),
            principal: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });

      it('passing IAgenteIntegracaoEstagio should create a new form with FormGroup', () => {
        const formGroup = service.createAgenteIntegracaoEstagioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cnpj: expect.any(Object),
            razaoSocial: expect.any(Object),
            coordenador: expect.any(Object),
            cpfCoordenadorEstagio: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            complemento: expect.any(Object),
            bairro: expect.any(Object),
            cep: expect.any(Object),
            principal: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });
    });

    describe('getAgenteIntegracaoEstagio', () => {
      it('should return NewAgenteIntegracaoEstagio for default AgenteIntegracaoEstagio initial value', () => {
        const formGroup = service.createAgenteIntegracaoEstagioFormGroup(sampleWithNewData);

        const agenteIntegracaoEstagio = service.getAgenteIntegracaoEstagio(formGroup) as any;

        expect(agenteIntegracaoEstagio).toMatchObject(sampleWithNewData);
      });

      it('should return NewAgenteIntegracaoEstagio for empty AgenteIntegracaoEstagio initial value', () => {
        const formGroup = service.createAgenteIntegracaoEstagioFormGroup();

        const agenteIntegracaoEstagio = service.getAgenteIntegracaoEstagio(formGroup) as any;

        expect(agenteIntegracaoEstagio).toMatchObject({});
      });

      it('should return IAgenteIntegracaoEstagio', () => {
        const formGroup = service.createAgenteIntegracaoEstagioFormGroup(sampleWithRequiredData);

        const agenteIntegracaoEstagio = service.getAgenteIntegracaoEstagio(formGroup) as any;

        expect(agenteIntegracaoEstagio).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAgenteIntegracaoEstagio should not enable id FormControl', () => {
        const formGroup = service.createAgenteIntegracaoEstagioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAgenteIntegracaoEstagio should disable id FormControl', () => {
        const formGroup = service.createAgenteIntegracaoEstagioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
