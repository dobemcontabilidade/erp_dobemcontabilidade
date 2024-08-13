import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dependentes-funcionario.test-samples';

import { DependentesFuncionarioFormService } from './dependentes-funcionario-form.service';

describe('DependentesFuncionario Form Service', () => {
  let service: DependentesFuncionarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DependentesFuncionarioFormService);
  });

  describe('Service methods', () => {
    describe('createDependentesFuncionarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDependentesFuncionarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlCertidaoDependente: expect.any(Object),
            urlRgDependente: expect.any(Object),
            dependenteIRRF: expect.any(Object),
            dependenteSalarioFamilia: expect.any(Object),
            tipoDependenteFuncionarioEnum: expect.any(Object),
            pessoa: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });

      it('passing IDependentesFuncionario should create a new form with FormGroup', () => {
        const formGroup = service.createDependentesFuncionarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlCertidaoDependente: expect.any(Object),
            urlRgDependente: expect.any(Object),
            dependenteIRRF: expect.any(Object),
            dependenteSalarioFamilia: expect.any(Object),
            tipoDependenteFuncionarioEnum: expect.any(Object),
            pessoa: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });
    });

    describe('getDependentesFuncionario', () => {
      it('should return NewDependentesFuncionario for default DependentesFuncionario initial value', () => {
        const formGroup = service.createDependentesFuncionarioFormGroup(sampleWithNewData);

        const dependentesFuncionario = service.getDependentesFuncionario(formGroup) as any;

        expect(dependentesFuncionario).toMatchObject(sampleWithNewData);
      });

      it('should return NewDependentesFuncionario for empty DependentesFuncionario initial value', () => {
        const formGroup = service.createDependentesFuncionarioFormGroup();

        const dependentesFuncionario = service.getDependentesFuncionario(formGroup) as any;

        expect(dependentesFuncionario).toMatchObject({});
      });

      it('should return IDependentesFuncionario', () => {
        const formGroup = service.createDependentesFuncionarioFormGroup(sampleWithRequiredData);

        const dependentesFuncionario = service.getDependentesFuncionario(formGroup) as any;

        expect(dependentesFuncionario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDependentesFuncionario should not enable id FormControl', () => {
        const formGroup = service.createDependentesFuncionarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDependentesFuncionario should disable id FormControl', () => {
        const formGroup = service.createDependentesFuncionarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
