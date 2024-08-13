import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../empresa-modelo.test-samples';

import { EmpresaModeloFormService } from './empresa-modelo-form.service';

describe('EmpresaModelo Form Service', () => {
  let service: EmpresaModeloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpresaModeloFormService);
  });

  describe('Service methods', () => {
    describe('createEmpresaModeloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmpresaModeloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            observacao: expect.any(Object),
            segmentoCnaes: expect.any(Object),
            ramo: expect.any(Object),
            enquadramento: expect.any(Object),
            tributacao: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });

      it('passing IEmpresaModelo should create a new form with FormGroup', () => {
        const formGroup = service.createEmpresaModeloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            observacao: expect.any(Object),
            segmentoCnaes: expect.any(Object),
            ramo: expect.any(Object),
            enquadramento: expect.any(Object),
            tributacao: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmpresaModelo', () => {
      it('should return NewEmpresaModelo for default EmpresaModelo initial value', () => {
        const formGroup = service.createEmpresaModeloFormGroup(sampleWithNewData);

        const empresaModelo = service.getEmpresaModelo(formGroup) as any;

        expect(empresaModelo).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmpresaModelo for empty EmpresaModelo initial value', () => {
        const formGroup = service.createEmpresaModeloFormGroup();

        const empresaModelo = service.getEmpresaModelo(formGroup) as any;

        expect(empresaModelo).toMatchObject({});
      });

      it('should return IEmpresaModelo', () => {
        const formGroup = service.createEmpresaModeloFormGroup(sampleWithRequiredData);

        const empresaModelo = service.getEmpresaModelo(formGroup) as any;

        expect(empresaModelo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmpresaModelo should not enable id FormControl', () => {
        const formGroup = service.createEmpresaModeloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmpresaModelo should disable id FormControl', () => {
        const formGroup = service.createEmpresaModeloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
