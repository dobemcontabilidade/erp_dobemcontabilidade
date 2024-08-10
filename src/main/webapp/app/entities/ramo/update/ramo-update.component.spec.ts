import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { RamoService } from '../service/ramo.service';
import { IRamo } from '../ramo.model';
import { RamoFormService } from './ramo-form.service';

import { RamoUpdateComponent } from './ramo-update.component';

describe('Ramo Management Update Component', () => {
  let comp: RamoUpdateComponent;
  let fixture: ComponentFixture<RamoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ramoFormService: RamoFormService;
  let ramoService: RamoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RamoUpdateComponent],
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
      .overrideTemplate(RamoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RamoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ramoFormService = TestBed.inject(RamoFormService);
    ramoService = TestBed.inject(RamoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ramo: IRamo = { id: 456 };

      activatedRoute.data = of({ ramo });
      comp.ngOnInit();

      expect(comp.ramo).toEqual(ramo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRamo>>();
      const ramo = { id: 123 };
      jest.spyOn(ramoFormService, 'getRamo').mockReturnValue(ramo);
      jest.spyOn(ramoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ramo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ramo }));
      saveSubject.complete();

      // THEN
      expect(ramoFormService.getRamo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ramoService.update).toHaveBeenCalledWith(expect.objectContaining(ramo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRamo>>();
      const ramo = { id: 123 };
      jest.spyOn(ramoFormService, 'getRamo').mockReturnValue({ id: null });
      jest.spyOn(ramoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ramo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ramo }));
      saveSubject.complete();

      // THEN
      expect(ramoFormService.getRamo).toHaveBeenCalled();
      expect(ramoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRamo>>();
      const ramo = { id: 123 };
      jest.spyOn(ramoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ramo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ramoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
