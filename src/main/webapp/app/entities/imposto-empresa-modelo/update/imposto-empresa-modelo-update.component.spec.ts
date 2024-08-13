import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { IImposto } from 'app/entities/imposto/imposto.model';
import { ImpostoService } from 'app/entities/imposto/service/imposto.service';
import { IImpostoEmpresaModelo } from '../imposto-empresa-modelo.model';
import { ImpostoEmpresaModeloService } from '../service/imposto-empresa-modelo.service';
import { ImpostoEmpresaModeloFormService } from './imposto-empresa-modelo-form.service';

import { ImpostoEmpresaModeloUpdateComponent } from './imposto-empresa-modelo-update.component';

describe('ImpostoEmpresaModelo Management Update Component', () => {
  let comp: ImpostoEmpresaModeloUpdateComponent;
  let fixture: ComponentFixture<ImpostoEmpresaModeloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let impostoEmpresaModeloFormService: ImpostoEmpresaModeloFormService;
  let impostoEmpresaModeloService: ImpostoEmpresaModeloService;
  let empresaModeloService: EmpresaModeloService;
  let impostoService: ImpostoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ImpostoEmpresaModeloUpdateComponent],
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
      .overrideTemplate(ImpostoEmpresaModeloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImpostoEmpresaModeloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    impostoEmpresaModeloFormService = TestBed.inject(ImpostoEmpresaModeloFormService);
    impostoEmpresaModeloService = TestBed.inject(ImpostoEmpresaModeloService);
    empresaModeloService = TestBed.inject(EmpresaModeloService);
    impostoService = TestBed.inject(ImpostoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EmpresaModelo query and add missing value', () => {
      const impostoEmpresaModelo: IImpostoEmpresaModelo = { id: 456 };
      const empresaModelo: IEmpresaModelo = { id: 10568 };
      impostoEmpresaModelo.empresaModelo = empresaModelo;

      const empresaModeloCollection: IEmpresaModelo[] = [{ id: 7250 }];
      jest.spyOn(empresaModeloService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaModeloCollection })));
      const additionalEmpresaModelos = [empresaModelo];
      const expectedCollection: IEmpresaModelo[] = [...additionalEmpresaModelos, ...empresaModeloCollection];
      jest.spyOn(empresaModeloService, 'addEmpresaModeloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ impostoEmpresaModelo });
      comp.ngOnInit();

      expect(empresaModeloService.query).toHaveBeenCalled();
      expect(empresaModeloService.addEmpresaModeloToCollectionIfMissing).toHaveBeenCalledWith(
        empresaModeloCollection,
        ...additionalEmpresaModelos.map(expect.objectContaining),
      );
      expect(comp.empresaModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Imposto query and add missing value', () => {
      const impostoEmpresaModelo: IImpostoEmpresaModelo = { id: 456 };
      const imposto: IImposto = { id: 27342 };
      impostoEmpresaModelo.imposto = imposto;

      const impostoCollection: IImposto[] = [{ id: 18495 }];
      jest.spyOn(impostoService, 'query').mockReturnValue(of(new HttpResponse({ body: impostoCollection })));
      const additionalImpostos = [imposto];
      const expectedCollection: IImposto[] = [...additionalImpostos, ...impostoCollection];
      jest.spyOn(impostoService, 'addImpostoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ impostoEmpresaModelo });
      comp.ngOnInit();

      expect(impostoService.query).toHaveBeenCalled();
      expect(impostoService.addImpostoToCollectionIfMissing).toHaveBeenCalledWith(
        impostoCollection,
        ...additionalImpostos.map(expect.objectContaining),
      );
      expect(comp.impostosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const impostoEmpresaModelo: IImpostoEmpresaModelo = { id: 456 };
      const empresaModelo: IEmpresaModelo = { id: 7787 };
      impostoEmpresaModelo.empresaModelo = empresaModelo;
      const imposto: IImposto = { id: 16573 };
      impostoEmpresaModelo.imposto = imposto;

      activatedRoute.data = of({ impostoEmpresaModelo });
      comp.ngOnInit();

      expect(comp.empresaModelosSharedCollection).toContain(empresaModelo);
      expect(comp.impostosSharedCollection).toContain(imposto);
      expect(comp.impostoEmpresaModelo).toEqual(impostoEmpresaModelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoEmpresaModelo>>();
      const impostoEmpresaModelo = { id: 123 };
      jest.spyOn(impostoEmpresaModeloFormService, 'getImpostoEmpresaModelo').mockReturnValue(impostoEmpresaModelo);
      jest.spyOn(impostoEmpresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoEmpresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: impostoEmpresaModelo }));
      saveSubject.complete();

      // THEN
      expect(impostoEmpresaModeloFormService.getImpostoEmpresaModelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(impostoEmpresaModeloService.update).toHaveBeenCalledWith(expect.objectContaining(impostoEmpresaModelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoEmpresaModelo>>();
      const impostoEmpresaModelo = { id: 123 };
      jest.spyOn(impostoEmpresaModeloFormService, 'getImpostoEmpresaModelo').mockReturnValue({ id: null });
      jest.spyOn(impostoEmpresaModeloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoEmpresaModelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: impostoEmpresaModelo }));
      saveSubject.complete();

      // THEN
      expect(impostoEmpresaModeloFormService.getImpostoEmpresaModelo).toHaveBeenCalled();
      expect(impostoEmpresaModeloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoEmpresaModelo>>();
      const impostoEmpresaModelo = { id: 123 };
      jest.spyOn(impostoEmpresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoEmpresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(impostoEmpresaModeloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmpresaModelo', () => {
      it('Should forward to empresaModeloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaModeloService, 'compareEmpresaModelo');
        comp.compareEmpresaModelo(entity, entity2);
        expect(empresaModeloService.compareEmpresaModelo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareImposto', () => {
      it('Should forward to impostoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(impostoService, 'compareImposto');
        comp.compareImposto(entity, entity2);
        expect(impostoService.compareImposto).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
