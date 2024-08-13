import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../servico-contabil-empresa-modelo.test-samples';

import { ServicoContabilEmpresaModeloFormService } from './servico-contabil-empresa-modelo-form.service';

describe('ServicoContabilEmpresaModelo Form Service', () => {
  let service: ServicoContabilEmpresaModeloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicoContabilEmpresaModeloFormService);
  });

  describe('Service methods', () => {
    describe('createServicoContabilEmpresaModeloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServicoContabilEmpresaModeloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            empresaModelo: expect.any(Object),
            servicoContabil: expect.any(Object),
          }),
        );
      });

      it('passing IServicoContabilEmpresaModelo should create a new form with FormGroup', () => {
        const formGroup = service.createServicoContabilEmpresaModeloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            empresaModelo: expect.any(Object),
            servicoContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getServicoContabilEmpresaModelo', () => {
      it('should return NewServicoContabilEmpresaModelo for default ServicoContabilEmpresaModelo initial value', () => {
        const formGroup = service.createServicoContabilEmpresaModeloFormGroup(sampleWithNewData);

        const servicoContabilEmpresaModelo = service.getServicoContabilEmpresaModelo(formGroup) as any;

        expect(servicoContabilEmpresaModelo).toMatchObject(sampleWithNewData);
      });

      it('should return NewServicoContabilEmpresaModelo for empty ServicoContabilEmpresaModelo initial value', () => {
        const formGroup = service.createServicoContabilEmpresaModeloFormGroup();

        const servicoContabilEmpresaModelo = service.getServicoContabilEmpresaModelo(formGroup) as any;

        expect(servicoContabilEmpresaModelo).toMatchObject({});
      });

      it('should return IServicoContabilEmpresaModelo', () => {
        const formGroup = service.createServicoContabilEmpresaModeloFormGroup(sampleWithRequiredData);

        const servicoContabilEmpresaModelo = service.getServicoContabilEmpresaModelo(formGroup) as any;

        expect(servicoContabilEmpresaModelo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServicoContabilEmpresaModelo should not enable id FormControl', () => {
        const formGroup = service.createServicoContabilEmpresaModeloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServicoContabilEmpresaModelo should disable id FormControl', () => {
        const formGroup = service.createServicoContabilEmpresaModeloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
