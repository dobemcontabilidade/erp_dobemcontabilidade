import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../departamento-empresa.test-samples';

import { DepartamentoEmpresaFormService } from './departamento-empresa-form.service';

describe('DepartamentoEmpresa Form Service', () => {
  let service: DepartamentoEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DepartamentoEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createDepartamentoEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDepartamentoEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pontuacao: expect.any(Object),
            depoimento: expect.any(Object),
            reclamacao: expect.any(Object),
            departamento: expect.any(Object),
            empresa: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });

      it('passing IDepartamentoEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createDepartamentoEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pontuacao: expect.any(Object),
            depoimento: expect.any(Object),
            reclamacao: expect.any(Object),
            departamento: expect.any(Object),
            empresa: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });
    });

    describe('getDepartamentoEmpresa', () => {
      it('should return NewDepartamentoEmpresa for default DepartamentoEmpresa initial value', () => {
        const formGroup = service.createDepartamentoEmpresaFormGroup(sampleWithNewData);

        const departamentoEmpresa = service.getDepartamentoEmpresa(formGroup) as any;

        expect(departamentoEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewDepartamentoEmpresa for empty DepartamentoEmpresa initial value', () => {
        const formGroup = service.createDepartamentoEmpresaFormGroup();

        const departamentoEmpresa = service.getDepartamentoEmpresa(formGroup) as any;

        expect(departamentoEmpresa).toMatchObject({});
      });

      it('should return IDepartamentoEmpresa', () => {
        const formGroup = service.createDepartamentoEmpresaFormGroup(sampleWithRequiredData);

        const departamentoEmpresa = service.getDepartamentoEmpresa(formGroup) as any;

        expect(departamentoEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDepartamentoEmpresa should not enable id FormControl', () => {
        const formGroup = service.createDepartamentoEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDepartamentoEmpresa should disable id FormControl', () => {
        const formGroup = service.createDepartamentoEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
