import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../servico-contabil-ordem-servico.test-samples';

import { ServicoContabilOrdemServicoFormService } from './servico-contabil-ordem-servico-form.service';

describe('ServicoContabilOrdemServico Form Service', () => {
  let service: ServicoContabilOrdemServicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicoContabilOrdemServicoFormService);
  });

  describe('Service methods', () => {
    describe('createServicoContabilOrdemServicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServicoContabilOrdemServicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAdmin: expect.any(Object),
            dataLegal: expect.any(Object),
            servicoContabil: expect.any(Object),
            ordemServico: expect.any(Object),
          }),
        );
      });

      it('passing IServicoContabilOrdemServico should create a new form with FormGroup', () => {
        const formGroup = service.createServicoContabilOrdemServicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAdmin: expect.any(Object),
            dataLegal: expect.any(Object),
            servicoContabil: expect.any(Object),
            ordemServico: expect.any(Object),
          }),
        );
      });
    });

    describe('getServicoContabilOrdemServico', () => {
      it('should return NewServicoContabilOrdemServico for default ServicoContabilOrdemServico initial value', () => {
        const formGroup = service.createServicoContabilOrdemServicoFormGroup(sampleWithNewData);

        const servicoContabilOrdemServico = service.getServicoContabilOrdemServico(formGroup) as any;

        expect(servicoContabilOrdemServico).toMatchObject(sampleWithNewData);
      });

      it('should return NewServicoContabilOrdemServico for empty ServicoContabilOrdemServico initial value', () => {
        const formGroup = service.createServicoContabilOrdemServicoFormGroup();

        const servicoContabilOrdemServico = service.getServicoContabilOrdemServico(formGroup) as any;

        expect(servicoContabilOrdemServico).toMatchObject({});
      });

      it('should return IServicoContabilOrdemServico', () => {
        const formGroup = service.createServicoContabilOrdemServicoFormGroup(sampleWithRequiredData);

        const servicoContabilOrdemServico = service.getServicoContabilOrdemServico(formGroup) as any;

        expect(servicoContabilOrdemServico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServicoContabilOrdemServico should not enable id FormControl', () => {
        const formGroup = service.createServicoContabilOrdemServicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServicoContabilOrdemServico should disable id FormControl', () => {
        const formGroup = service.createServicoContabilOrdemServicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
