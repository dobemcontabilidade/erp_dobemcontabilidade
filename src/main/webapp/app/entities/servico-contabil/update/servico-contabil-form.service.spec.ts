import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../servico-contabil.test-samples';

import { ServicoContabilFormService } from './servico-contabil-form.service';

describe('ServicoContabil Form Service', () => {
  let service: ServicoContabilFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicoContabilFormService);
  });

  describe('Service methods', () => {
    describe('createServicoContabilFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServicoContabilFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            valor: expect.any(Object),
            descricao: expect.any(Object),
            diasExecucao: expect.any(Object),
            geraMulta: expect.any(Object),
            periodoExecucao: expect.any(Object),
            diaLegal: expect.any(Object),
            mesLegal: expect.any(Object),
            valorRefMulta: expect.any(Object),
            areaContabil: expect.any(Object),
            esfera: expect.any(Object),
          }),
        );
      });

      it('passing IServicoContabil should create a new form with FormGroup', () => {
        const formGroup = service.createServicoContabilFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            valor: expect.any(Object),
            descricao: expect.any(Object),
            diasExecucao: expect.any(Object),
            geraMulta: expect.any(Object),
            periodoExecucao: expect.any(Object),
            diaLegal: expect.any(Object),
            mesLegal: expect.any(Object),
            valorRefMulta: expect.any(Object),
            areaContabil: expect.any(Object),
            esfera: expect.any(Object),
          }),
        );
      });
    });

    describe('getServicoContabil', () => {
      it('should return NewServicoContabil for default ServicoContabil initial value', () => {
        const formGroup = service.createServicoContabilFormGroup(sampleWithNewData);

        const servicoContabil = service.getServicoContabil(formGroup) as any;

        expect(servicoContabil).toMatchObject(sampleWithNewData);
      });

      it('should return NewServicoContabil for empty ServicoContabil initial value', () => {
        const formGroup = service.createServicoContabilFormGroup();

        const servicoContabil = service.getServicoContabil(formGroup) as any;

        expect(servicoContabil).toMatchObject({});
      });

      it('should return IServicoContabil', () => {
        const formGroup = service.createServicoContabilFormGroup(sampleWithRequiredData);

        const servicoContabil = service.getServicoContabil(formGroup) as any;

        expect(servicoContabil).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServicoContabil should not enable id FormControl', () => {
        const formGroup = service.createServicoContabilFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServicoContabil should disable id FormControl', () => {
        const formGroup = service.createServicoContabilFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
