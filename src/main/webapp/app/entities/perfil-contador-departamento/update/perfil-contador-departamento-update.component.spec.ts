import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';
import { PerfilContadorService } from 'app/entities/perfil-contador/service/perfil-contador.service';
import { IPerfilContadorDepartamento } from '../perfil-contador-departamento.model';
import { PerfilContadorDepartamentoService } from '../service/perfil-contador-departamento.service';
import { PerfilContadorDepartamentoFormService } from './perfil-contador-departamento-form.service';

import { PerfilContadorDepartamentoUpdateComponent } from './perfil-contador-departamento-update.component';

describe('PerfilContadorDepartamento Management Update Component', () => {
  let comp: PerfilContadorDepartamentoUpdateComponent;
  let fixture: ComponentFixture<PerfilContadorDepartamentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let perfilContadorDepartamentoFormService: PerfilContadorDepartamentoFormService;
  let perfilContadorDepartamentoService: PerfilContadorDepartamentoService;
  let departamentoService: DepartamentoService;
  let perfilContadorService: PerfilContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PerfilContadorDepartamentoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PerfilContadorDepartamentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PerfilContadorDepartamentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    perfilContadorDepartamentoFormService = TestBed.inject(PerfilContadorDepartamentoFormService);
    perfilContadorDepartamentoService = TestBed.inject(PerfilContadorDepartamentoService);
    departamentoService = TestBed.inject(DepartamentoService);
    perfilContadorService = TestBed.inject(PerfilContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Departamento query and add missing value', () => {
      const perfilContadorDepartamento: IPerfilContadorDepartamento = { id: 456 };
      const departamento: IDepartamento = { id: 7299 };
      perfilContadorDepartamento.departamento = departamento;

      const departamentoCollection: IDepartamento[] = [{ id: 16037 }];
      jest.spyOn(departamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: departamentoCollection })));
      const additionalDepartamentos = [departamento];
      const expectedCollection: IDepartamento[] = [...additionalDepartamentos, ...departamentoCollection];
      jest.spyOn(departamentoService, 'addDepartamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ perfilContadorDepartamento });
      comp.ngOnInit();

      expect(departamentoService.query).toHaveBeenCalled();
      expect(departamentoService.addDepartamentoToCollectionIfMissing).toHaveBeenCalledWith(
        departamentoCollection,
        ...additionalDepartamentos.map(expect.objectContaining),
      );
      expect(comp.departamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PerfilContador query and add missing value', () => {
      const perfilContadorDepartamento: IPerfilContadorDepartamento = { id: 456 };
      const perfilContador: IPerfilContador = { id: 9489 };
      perfilContadorDepartamento.perfilContador = perfilContador;

      const perfilContadorCollection: IPerfilContador[] = [{ id: 21269 }];
      jest.spyOn(perfilContadorService, 'query').mockReturnValue(of(new HttpResponse({ body: perfilContadorCollection })));
      const additionalPerfilContadors = [perfilContador];
      const expectedCollection: IPerfilContador[] = [...additionalPerfilContadors, ...perfilContadorCollection];
      jest.spyOn(perfilContadorService, 'addPerfilContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ perfilContadorDepartamento });
      comp.ngOnInit();

      expect(perfilContadorService.query).toHaveBeenCalled();
      expect(perfilContadorService.addPerfilContadorToCollectionIfMissing).toHaveBeenCalledWith(
        perfilContadorCollection,
        ...additionalPerfilContadors.map(expect.objectContaining),
      );
      expect(comp.perfilContadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const perfilContadorDepartamento: IPerfilContadorDepartamento = { id: 456 };
      const departamento: IDepartamento = { id: 8814 };
      perfilContadorDepartamento.departamento = departamento;
      const perfilContador: IPerfilContador = { id: 8371 };
      perfilContadorDepartamento.perfilContador = perfilContador;

      activatedRoute.data = of({ perfilContadorDepartamento });
      comp.ngOnInit();

      expect(comp.departamentosSharedCollection).toContain(departamento);
      expect(comp.perfilContadorsSharedCollection).toContain(perfilContador);
      expect(comp.perfilContadorDepartamento).toEqual(perfilContadorDepartamento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilContadorDepartamento>>();
      const perfilContadorDepartamento = { id: 123 };
      jest.spyOn(perfilContadorDepartamentoFormService, 'getPerfilContadorDepartamento').mockReturnValue(perfilContadorDepartamento);
      jest.spyOn(perfilContadorDepartamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilContadorDepartamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilContadorDepartamento }));
      saveSubject.complete();

      // THEN
      expect(perfilContadorDepartamentoFormService.getPerfilContadorDepartamento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(perfilContadorDepartamentoService.update).toHaveBeenCalledWith(expect.objectContaining(perfilContadorDepartamento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilContadorDepartamento>>();
      const perfilContadorDepartamento = { id: 123 };
      jest.spyOn(perfilContadorDepartamentoFormService, 'getPerfilContadorDepartamento').mockReturnValue({ id: null });
      jest.spyOn(perfilContadorDepartamentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilContadorDepartamento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilContadorDepartamento }));
      saveSubject.complete();

      // THEN
      expect(perfilContadorDepartamentoFormService.getPerfilContadorDepartamento).toHaveBeenCalled();
      expect(perfilContadorDepartamentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilContadorDepartamento>>();
      const perfilContadorDepartamento = { id: 123 };
      jest.spyOn(perfilContadorDepartamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilContadorDepartamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(perfilContadorDepartamentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDepartamento', () => {
      it('Should forward to departamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(departamentoService, 'compareDepartamento');
        comp.compareDepartamento(entity, entity2);
        expect(departamentoService.compareDepartamento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePerfilContador', () => {
      it('Should forward to perfilContadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(perfilContadorService, 'comparePerfilContador');
        comp.comparePerfilContador(entity, entity2);
        expect(perfilContadorService.comparePerfilContador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
