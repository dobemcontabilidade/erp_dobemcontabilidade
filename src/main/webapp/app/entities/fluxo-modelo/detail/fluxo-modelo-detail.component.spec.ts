import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FluxoModeloDetailComponent } from './fluxo-modelo-detail.component';

describe('FluxoModelo Management Detail Component', () => {
  let comp: FluxoModeloDetailComponent;
  let fixture: ComponentFixture<FluxoModeloDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FluxoModeloDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FluxoModeloDetailComponent,
              resolve: { fluxoModelo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FluxoModeloDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FluxoModeloDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fluxoModelo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FluxoModeloDetailComponent);

      // THEN
      expect(instance.fluxoModelo()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
