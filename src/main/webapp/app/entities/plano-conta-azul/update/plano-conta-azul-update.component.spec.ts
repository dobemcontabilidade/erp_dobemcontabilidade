import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PlanoContaAzulService } from '../service/plano-conta-azul.service';
import { IPlanoContaAzul } from '../plano-conta-azul.model';
import { PlanoContaAzulFormService } from './plano-conta-azul-form.service';

import { PlanoContaAzulUpdateComponent } from './plano-conta-azul-update.component';

describe('PlanoContaAzul Management Update Component', () => {
  let comp: PlanoContaAzulUpdateComponent;
  let fixture: ComponentFixture<PlanoContaAzulUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planoContaAzulFormService: PlanoContaAzulFormService;
  let planoContaAzulService: PlanoContaAzulService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PlanoContaAzulUpdateComponent],
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
      .overrideTemplate(PlanoContaAzulUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanoContaAzulUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planoContaAzulFormService = TestBed.inject(PlanoContaAzulFormService);
    planoContaAzulService = TestBed.inject(PlanoContaAzulService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const planoContaAzul: IPlanoContaAzul = { id: 456 };

      activatedRoute.data = of({ planoContaAzul });
      comp.ngOnInit();

      expect(comp.planoContaAzul).toEqual(planoContaAzul);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoContaAzul>>();
      const planoContaAzul = { id: 123 };
      jest.spyOn(planoContaAzulFormService, 'getPlanoContaAzul').mockReturnValue(planoContaAzul);
      jest.spyOn(planoContaAzulService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoContaAzul });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoContaAzul }));
      saveSubject.complete();

      // THEN
      expect(planoContaAzulFormService.getPlanoContaAzul).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planoContaAzulService.update).toHaveBeenCalledWith(expect.objectContaining(planoContaAzul));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoContaAzul>>();
      const planoContaAzul = { id: 123 };
      jest.spyOn(planoContaAzulFormService, 'getPlanoContaAzul').mockReturnValue({ id: null });
      jest.spyOn(planoContaAzulService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoContaAzul: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoContaAzul }));
      saveSubject.complete();

      // THEN
      expect(planoContaAzulFormService.getPlanoContaAzul).toHaveBeenCalled();
      expect(planoContaAzulService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoContaAzul>>();
      const planoContaAzul = { id: 123 };
      jest.spyOn(planoContaAzulService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoContaAzul });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planoContaAzulService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
