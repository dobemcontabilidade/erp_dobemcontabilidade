import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../contador-responsavel-ordem-servico.test-samples';

import { ContadorResponsavelOrdemServicoFormService } from './contador-responsavel-ordem-servico-form.service';

describe('ContadorResponsavelOrdemServico Form Service', () => {
  let service: ContadorResponsavelOrdemServicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContadorResponsavelOrdemServicoFormService);
  });

  describe('Service methods', () => {
    describe('createContadorResponsavelOrdemServicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContadorResponsavelOrdemServicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAtribuicao: expect.any(Object),
            dataRevogacao: expect.any(Object),
            tarefaOrdemServicoExecucao: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });

      it('passing IContadorResponsavelOrdemServico should create a new form with FormGroup', () => {
        const formGroup = service.createContadorResponsavelOrdemServicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAtribuicao: expect.any(Object),
            dataRevogacao: expect.any(Object),
            tarefaOrdemServicoExecucao: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });
    });

    describe('getContadorResponsavelOrdemServico', () => {
      it('should return NewContadorResponsavelOrdemServico for default ContadorResponsavelOrdemServico initial value', () => {
        const formGroup = service.createContadorResponsavelOrdemServicoFormGroup(sampleWithNewData);

        const contadorResponsavelOrdemServico = service.getContadorResponsavelOrdemServico(formGroup) as any;

        expect(contadorResponsavelOrdemServico).toMatchObject(sampleWithNewData);
      });

      it('should return NewContadorResponsavelOrdemServico for empty ContadorResponsavelOrdemServico initial value', () => {
        const formGroup = service.createContadorResponsavelOrdemServicoFormGroup();

        const contadorResponsavelOrdemServico = service.getContadorResponsavelOrdemServico(formGroup) as any;

        expect(contadorResponsavelOrdemServico).toMatchObject({});
      });

      it('should return IContadorResponsavelOrdemServico', () => {
        const formGroup = service.createContadorResponsavelOrdemServicoFormGroup(sampleWithRequiredData);

        const contadorResponsavelOrdemServico = service.getContadorResponsavelOrdemServico(formGroup) as any;

        expect(contadorResponsavelOrdemServico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContadorResponsavelOrdemServico should not enable id FormControl', () => {
        const formGroup = service.createContadorResponsavelOrdemServicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContadorResponsavelOrdemServico should disable id FormControl', () => {
        const formGroup = service.createContadorResponsavelOrdemServicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
